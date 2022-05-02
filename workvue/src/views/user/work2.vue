<template>
  <div class="index">
       <div class="index-left" style="flex:3;padding-left:30px">
        <div style="border-bottom: 1px solid #e9ecee">
           <div style="float:right;padding-right:50px">
          <el-button  @click=" this.$router.push('/work')" type="primary">返回</el-button>
          </div>
        <h1>{{position.title}}</h1> 
        <h3>专业领域:{{position.category}}</h3>
        </div>
        <div style="border-bottom: 1px solid #e9ecee">
        <h2>酬金价格</h2>
        <p>项目预算&nbsp;&nbsp;&nbsp;&nbsp;{{position.money}}元</p>
        <p>项目期限&nbsp;&nbsp;&nbsp;&nbsp;{{position.time}}天 </p>
        </div>
        <div style="">
        <h2>需求信息</h2>
         
         <div id="zhi" style="font-size:17px"></div>
        </div>
        <hr/>
        <div style="float:right;padding-right:30px">
           <p>工作者已完成接单</p>
           <el-button type="text" @click="dialogFormVisible=true">获取负责人联系方式</el-button>
         </div>
         <p>雇佣者</p>
         <p>{{user.name}}</p>
         <el-button type="text" @click="udown()">放弃任务</el-button>
      </div>
       <el-dialog v-model="dialogFormVisible" title="">
         <h3>负责人:{{position.people}}</h3>
         <h3>联系方式:{{position.phone}}</h3>
         <h3>联系邮箱:{{position.email}}</h3>
         <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">确认</el-button>
      </span>
    </template>
  </el-dialog>
  <el-dialog v-model="dialogFormVisible2" title="中断任务">
    <el-form :model="position">
      <el-form-item label="放弃任务理由" :label-width="formLabelWidth">
        <el-input v-model="position.msg" autocomplete="off" />
      </el-form-item>
     
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible2 = false">取消</el-button>
        <el-button type="primary" @click="que()"
          >确认</el-button
        >
      </span>
    </template>
  </el-dialog>
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
.el-input{
  width: 300px;
}

</style>
<script>
import axioss from '../../utils/axios'
export default {
  name: 'employ2',
  data() {
      return {
          key:'',
          dialogFormVisible:false,
          dialogFormVisible2:false,
          position:{},
          user:{},
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
            this.user = res.data.data2
            document.getElementById("zhi").innerHTML=this.position.content
              if(this.position.cover2){
                this.loads=1
              }
            }else{
              this.$message({
            type:"error",
            message:"服务器故障"
          })
            }
      })
      },
      udown(){
        this.dialogFormVisible2=true
      },
      que(){
        axioss.post("/work/fangqi1",this.position).then(res=>{
                  if(res.data.code==="0"){
                  this.$router.push("/giveup0")
                  this.$message({
                    type:"success",
                    message:"等待审核中。。"
                  })
                  }
                })
        dialogFormVisible2 = false
      },
      uploads(){
        this.load()
      },
  },
}
</script>