<template>
    <div class="index" v-loading="loading">
      <div class="index-left" style="flex:3;padding-left:30px;">
        <div style="border-bottom: 1px solid #e9ecee">
          <br/>
          <div style="float:right;padding-right:50px">
          <el-button  @click=" this.$router.push('/')" type="primary">返回</el-button>
          </div>
        <h1>{{position.title}}</h1> 
        <br/>
        <h3>专业领域:{{position.category}}</h3><br/>
        </div>
        <div style="border-bottom: 1px solid #e9ecee">
        <h2>酬金价格</h2>
        <p>项目预算&nbsp;&nbsp;&nbsp;&nbsp;{{position.money}}元</p>
        <p>项目期限&nbsp;&nbsp;&nbsp;&nbsp;{{position.time}}天 </p>
        </div>
        <br/>
        <div style="">
        <h2>需求信息</h2><br/>
        <div id="zhi" style="font-size:17px"></div>
        <br/>
        </div>
        
      </div>
      <div class="index-right"  style="flex:1;border-left: 30px solid #f6f8f9;">
        <div style="width:100%;height:100px;">
          <div align="center" style="padding-top:15px">
        <el-button v-if="position.flag==0" style="width:80%;height:60px;" type="primary" disabled>挂载中</el-button>
        <el-button v-else-if="position.flag==1" style="width:15vw;height:8vh;margin:0 0;margin:auto;" type="primary" disabled>已申请</el-button>
        <el-button v-else style="width:15vw;height:8vh;margin:0 0;margin:auto;" type="primary" @click="add()">立刻申请</el-button>
          </div>
        </div>
        <hr style="border: 0.5px solid #e9ecee">
        <h3>关于雇主</h3>
        {{u.name}}
        <br>
        <hr style="border: 0.5px solid #e9ecee">
         <br>
        <br>
        <div style="width:150px;left:0px;right:0px;margin:auto;">
        <img src="../../assets/pic/a.png" width="130" height="130" object-fit="cover"> 
        <h4 style="text-align:center;"></h4>
        <p style="font-size:10px;font-family:'楷体'"></p>
        <br>
       
        <img src="../../assets/pic/c.png" width="130" height="130" object-fit="cover">
        <h4 style="text-align:center;">公共诚信保障</h4>
        <p style="font-size:10px;font-family:'楷体'">兼职社区致力于维护双方真实的交易，保证双方利益与平台公平性</p>
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
</style>
<script scoped>
import router from "@/router";
import axioss from '../../utils/axios'
export default {
  name: 'indexposition',
  data() {
    return {
      loading:true,
     key:'',
     userid:'',
     position:{},
     u:{},
     user:{},
     p:{
       position:{},
       user:{}
     }
    }
  },
  created() {
    this.load()
  },
  methods: {
    load(){

      // 页面数据
      this.key = this.$route.params.key
      axioss.post("/position/querypositionbyid/"+this.key).then(res=>{
        this.position = res.data.data
        this.position.flag = 2
        document.getElementById("zhi").innerHTML=this.position.content
        axioss.post("/user/sel/"+this.position.userid).then(res=>{
        this.u = res.data.data
        this.loading=false
        })
          if(sessionStorage.getItem('user')){
          let userStr = sessionStorage.getItem('user') || {}
          this.user = JSON.parse(userStr)
          this.userid = this.user.id
          // 用户数据
          axioss.post("/user/query/"+this.user.id+"/"+this.position.id).then(res2=>{
            if(res.data.code=="0"){
              if(this.position.userid===this.userid){
                this.position.flag = 0
              }else{
              this.position.flag = res2.data.data
              }
            }else{
              this.$message({
                  type:'error',
                  message:"有异常"
                })
            }
          })
          }
      })
    },
    add(){
      if(sessionStorage.getItem('user')){
        let userStr = sessionStorage.getItem('user')
        this.user = JSON.parse(userStr)
        this.p.position=this.position
        this.p.user = this.user
        axioss.post('/position/shenqing',this.p).then(res=>{
          if(res.data.code==='0'){
            this.$message({
              type:'success',
              message:'成功'
            })
            this.load()
          }else{
            this.$message({
              type:'error',
              message:res.data.msg
            })
          }
        })
      }else{
        this.$message({
              type:'error',
              message:"请登录"
            })
        this.$router.push('/login')
      }
    }
  },
}
</script>