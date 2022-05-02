<template>
  <div class="index">
       <div class="index-left" style="flex:3;padding-left:30px">
        <div style="border-bottom: 1px solid #e9ecee">
            <div style="float:right;padding-right:50px;">
                <el-button type="info" v-if="message!=''">{{message}}</el-button>
                <el-button type="info" v-else>异常</el-button>
                 <el-button  @click=" this.$router.push('/giveup')" type="primary">返回</el-button>
                </div>
        <h1>{{position.title}}</h1> 
        <h3>专业领域:{{position.category}}</h3>
        </div>
        <div style="border-bottom: 1px solid #e9ecee">
        <h2>酬金价格</h2>
        <p>项目预算&nbsp;&nbsp;&nbsp;&nbsp;{{position.money}}元</p>
        <p>项目期限&nbsp;&nbsp;&nbsp;&nbsp;{{position.time}}天 </p>
        <p>放弃理由&nbsp;&nbsp;&nbsp;&nbsp;{{position.msg}} </p>
        </div>
        <div style="">
        <h2>需求信息</h2>
         
         <div id="zhi" style="font-size:17px"></div>
        </div>
        <hr/>
         <p>雇佣者:&nbsp;&nbsp;&nbsp;{{user.name}}</p>
      </div>
      <div class="index-right"  style="flex:1;border-left:30px solid #f6f8f9;">
      <br>
        <br>
        <div style="width:150px;left:0px;right:0px;margin:auto;">
        <img src="../../assets/pic/a.png" width="130" height="130" object-fit="cover"> 
        <h4 style="text-align:center;">双方实名认证</h4>
        <p style="font-size:10px;font-family:'楷体'">个人实行实名认证，企业实行企业营业执照认证，保证双方信息真实</p>
        <br>
       
        <img src="../../assets/pic/c.png" width="130" height="130" object-fit="cover">
        <h4 style="text-align:center;">公共诚信保障</h4>
        <p style="font-size:10px;font-family:'楷体'">兼职社区致力于维护双方真实的交易评价，保证双方利益与平台公平性</p>
        </div>
   
      </div>
    </div>
 
</template>

<style scoped>
.index{
    width: 70%;
    height: 100vh;
    left: 0px;
    right: 0px;
    margin: auto;
    display: flex;
    
}
.n:hover{
  background-color: rgb(102, 99, 99);
}

</style>
<script>
import axioss from '../../utils/axios'
export default {
  name: 'giveup2',
  data() {
      return {
          key:'',
          dialogFormVisible:false,
          position:{},
          user:{},
          message:''
      }
  },
  created(){
      this.load()
  },
  methods: {
      load(){
          this.key = this.$route.params.key
          this.upload=this.upload+this.key
          this.downs=this.downs+this.key
          axioss.post("/work/work2/"+this.key).then(res=>{
              if(res.data.code ==='0'){
                this.position = res.data.data
                if(this.position.udown==1){
                    this.message="此任务为用户放弃！"
                }else{
                    this.message="此任务为发布者放弃！"
                }
                this.user = res.data.data2
                document.getElementById("zhi").innerHTML=this.position.content
            }else{
              this.$message({
            type:"error",
            message:"服务器故障"
          })
            }
      })
      },
      down(){
        if(this.position.fabu2!=""){
          window.open(this.downs)
          this.$message({
            type:"success",
            message:"下载成功"
          })
        }else{
          this.$message({
            type:"error",
            message:"文件未上传!"
          })
        }
      },
      uploads(){
        this.load()
      },
  },
}
</script>