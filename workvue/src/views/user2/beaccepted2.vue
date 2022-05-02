<template>
  <div class="index">
      <div class="index-left" style="flex:3;padding-left:30px">
        <div style="border-bottom: 1px solid #e9ecee">
          <div style="float:right;padding-right:50px">
          <el-button  @click=" this.$router.push('/beaccepted')" type="primary">返回</el-button>
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
         <h4>申请信息</h4>
         <ul v-infinite-scroll="load" class="infinite-list" style="overflow: auto">
          <li v-for="(u,key) in users" :key="key" @click="info(u.id)"  class="infinite-list-item">
          <h3 style="padding-left:20px">申请人:&nbsp;&nbsp;{{u.name}}</h3>
          
          </li>
        </ul>
      </div>
      <div class="index-right"  style="flex:1;">
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
 
<!-- user的信息弹窗 -->

  <el-dialog v-model="dialogVisible" title="投递者信息" width="60%" draggable>
    <h1>{{user.name}}</h1>
    <h3>性别:{{user.sex}}</h3>
    <h3>邮箱:{{user.email}}</h3>
    <el-button type="primary" @click="down(user.resume)">简历详情</el-button>
    <el-dialog v-model="dialogVisible2">
    <img w-full :src="user.resume" alt="Preview Image" />
  </el-dialog>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button @click="no(position.id,user.id)">拒绝</el-button>
        <el-button type="primary" @click="yes(position.id,user.id)">同意该投递者接取任务</el-button>
      </span>
    </template>
  </el-dialog>
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
.index-right{
  border-left:30px solid #f6f8f9;
}
.infinite-list {
  
  padding: 0;
  margin: 0;
  list-style: none;
}
.infinite-list-item{
   height: 60px;
   border: 1.5px solid #e9ecee;
}
.infinite-list-item:hover{
  background-color: rgb(242,249,254);
}
</style>
<script>
import axioss from '../../utils/axios'
import router from "@/router";
export default {
  name: 'beaccepted2',
  data() {
      return {
          dialogVisible: false,
          key:'',
          position:{},
          user:{},
          users:[],
          dialogVisible2:false
      }
  },
  created(){
      this.load()
  },
  methods: {
      load(){
          this.key = this.$route.params.key
          axioss.post("/position/beaccepted2/"+this.key).then(res=>{
              if(res.data.code ==='0'){
            this.position = res.data.data2
            this.users = res.data.data
            document.getElementById("zhi").innerHTML=this.position.content
            }
      })
      },
      info(id){
        axioss.get("/user/querybyid/"+id).then(res=>{
        this.user = res.data.data
        this.dialogVisible = true
        })
        },
        yes(id,userid){
          this.dialogVisible = false
          axioss.get('/position/yesbe/'+id+'/'+userid).then(res=>{
            if(res.data.code==='0'){
              this.$router.push('/employ')
                this.$message({
                type: "success",
                message: "成功",
                });
            }else{
              this.load()
              this.$message({
                type: "error",
                message: "失败",
                });
            }
          })
        },
        no(id,userid){
          this.dialogVisible = false
          axioss.get('/position/nobe/'+id+'/'+userid).then(res=>{
            if(res.data.code==='0'){
              router.push('/indexposition/'+id)
                this.$message({
                type: "success",
                message: "成功",
                });
            }else{
              this.$message({
                type: "error",
                message: "失败",
                });
            }
          })
        },
        down(resume){
             window.location.href=resume
        }
  },
}
</script>