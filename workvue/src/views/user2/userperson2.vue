<template>
<div v-loading="loading">
 <h1>基本信息</h1>
 <el-upload
    class="upload-demo"
    style="float:right;padding-right:40px;"
    :action="pic"
    :on-success="uploads"
  >
    <el-button type="primary">上传头像</el-button>
    <template #tip>
    </template>
  </el-upload>
   <div class="block" v-if="pics">
          <el-avatar :size="100" :src="pics"></el-avatar>
    </div>
  <div style="width:400px;height:60px;display:flex;">
  <h3 style="flex:1;padding-top:15px">昵称</h3>
  <h4 style="flex:1;padding-top:17px">{{user.name}}</h4>
  </div><br>
   <div style="width:400px;height:60px;display:flex;">
  <h3 style="flex:1;padding-top:15px">用户名</h3>
  <h4 style="flex:1;padding-top:17px">{{user.username}}</h4>
  </div><br>
   <div style="width:400px;height:60px;display:flex;">
  <h3 style="flex:1;padding-top:15px">性别</h3>
  <h4 style="flex:1;padding-top:17px">{{user.sex}}</h4>
  </div><br>
   <div style="width:400px;height:60px;display:flex;">
  <h3 style="flex:1;padding-top:15px">邮箱</h3>
  <h4 style="flex:1;padding-top:17px">{{user.email}}</h4>
  </div><br>
  <div style="width:400px;height:60px;display:flex;">
  <h3 style="flex:1;padding-top:15px">注册时间</h3>
  <h5 style="flex:1;padding-top:17px" v-html="formatDate(user.createtime, 'yyyy年 MM月 dd日')"></h5>
  </div><br>
  <div style="float:right;padding-right:40px;">
        <el-button type="primary" @click="dialogFormVisible4=true" round>修改密码</el-button><br><br>
    <el-button type="primary" @click="dialogFormVisible=true" round>信息更改</el-button>
  </div>
  <!-- 修改弹窗 -->
  <el-dialog v-model="dialogFormVisible" width="50%" title="" center>
      <div style="width:55%;margin:0 0;margin:auto;">
    <el-form :model="user">
      <el-form-item label="昵称" :label-width="formLabelWidth">
        <el-input v-model="user.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="用户名" :label-width="formLabelWidth">
        <el-input v-model="user.username" autocomplete="off"></el-input>
      </el-form-item>
       <el-form-item label="性别" prop="sex">
        <el-radio-group v-model="user.sex">
          <el-radio label="男"></el-radio>
          <el-radio label="女"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="邮箱" :label-width="formLabelWidth">
        <el-input v-model="user.email" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="update()">确认</el-button>
      </span>
    </template>
  </el-dialog>
    <!-- 修改密码 -->
  <el-dialog v-model="dialogFormVisible4" title="修改密码">
    <el-form :model="passs2">
      <el-form-item label="原密码" prop="pass1" :label-width="formLabelWidth">
        <el-input v-model="passs2.pass1" type="password"  autocomplete="off" />
      </el-form-item>
    <el-form-item label="新密码" prop="pass2" :label-width="formLabelWidth">
        <el-input v-model="passs2.pass2" type="password"  autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible4 = false">取消</el-button>
        <el-button type="primary" @click="pass()"
          >确认</el-button
        >
      </span>
    </template>
  </el-dialog>
</div>
</template>

<style scoped>
.el-input{
  width: 300px;
}
</style>
<script>
import axioss from '../../utils/axios'
import formatDate from '../../utils/time'

export default {
  name: 'userperson2',
  data() {
      return {
        loading:true,
          dialogFormVisible:false,
           dialogFormVisible4:false,
          user:{},
          user2:{},
          pic:"http://localhost:9090/pic/upload/",
          pics:"",
          passs2:{
            id:'',
            pass1:'',
            pass2:''
          },
      }
  },
  computed:{
      formatDate(){
        return formatDate;
      }

    },
  created(){
       this.load()
  },
  methods: {
      load(){
        this.loading=true
          let userStr = sessionStorage.getItem("user") || "{}"
          this.user = JSON.parse(userStr)
          if(this.user){
              axioss.get("/user/querybyid/"+this.user.id).then(res=>{
                  if(res.data.code==="0"){
                      sessionStorage.setItem("user",JSON.stringify(res.data.data))
                      let userStr = sessionStorage.getItem("user") || "{}"
                      this.user = JSON.parse(userStr)
                      this.pic = this.pic+this.user.id
                      this.resume = this.resume+this.user.id
                      this.pics = this.user.pic
                      this.loading=false
                  }else{
                      this.$message({
                        type: "error",
                        message:"获取数据失败"
                    })
                  }
              })
          }else{
               this.$message({
                        type: "error",
                        message:"请登录"
                    })
                this.$router.push('/login')
          }
      },
      update(){
          if(this.user){
              axioss.put("/user/adminupdate",this.user2).then(res=>{
                  if(res.data.code=="0"){
                    axioss.post('/user/sel/'+this.user2.id).then(res=>{
                    if(res.data.code === '0'){
                        this.$message({
                        type:"success",
                        message:"成功"
                        })
                        sessionStorage.setItem("user",JSON.stringify(res.data.data))
                        this.load()
                        this.dialogFormVisible = false
                        }
                    })
                  }else{
                      this.$message({
                        type: "error",
                        message:"数据异常"
                    })
                  }
              })
          }
      },
      uploads(){
      location.reload()
      },
      pass(){
        this.passs2.id =  this.user.id
        axioss.post("/user/pass",this.passs2).then(res=>{
          if(res.data.code==="0"){
            this.$message({
                        type: "success",
                        message:res.data.msg
                    })
          }else{
             this.$message({
                        type: "error",
                        message:res.data.msg
                    })
          }
        })
        this.dialogFormVisible4=false
      }
  },
  
}
</script>