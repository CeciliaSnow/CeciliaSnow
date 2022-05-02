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
  </div>
   <div style="width:400px;height:60px;display:flex;">
  <h3 style="flex:1;padding-top:15px">用户名</h3>
  <h4 style="flex:1;padding-top:17px">{{user.username}}</h4>
  </div>
   <div style="width:400px;height:60px;display:flex;">
  <h3 style="flex:1;padding-top:15px">性别</h3>
  <h4 style="flex:1;padding-top:17px">{{user.sex}}</h4>
  </div>
   <div style="width:400px;height:60px;display:flex;">
  <h3 style="flex:1;padding-top:15px">邮箱</h3>
  <h4 style="flex:1;padding-top:17px">{{user.email}}</h4>
  </div>
  <!-- 上传简历 -->
  <h3 style="padding-top:15px">简历</h3>
  <div>
    <el-upload
    :action="resume"
    list-type="picture-card"
    :on-preview="handlePictureCardPreview"
    :on-remove="handleRemove"
    :file-list="fileList"
    :on-success="up"
  >
    <el-icon><Plus /></el-icon>
  </el-upload>
  <el-dialog v-model="dialogVisible">
    <img w-full :src="resume2" alt="Preview Image" />
  </el-dialog>
  </div>
  <div style="width:400px;height:60px;display:flex;">
  <h3 style="flex:1;padding-top:15px">注册时间</h3>
  <h5 style="flex:1;padding-top:17px" v-html="formatDate(user.createtime, 'yyyy年 MM月 dd日')"></h5>
  </div><br>
  <div style="float:right;padding-right:40px;">
    <el-button type="primary" @click="dialogFormVisible3=true" round>修改密码</el-button><br><br>
    <el-button type="primary" @click="dialogFormVisible=true" round>信息更改</el-button>
  </div>
  <div style="height:200px"></div>
  <!-- 修改密码 -->
  <el-dialog v-model="dialogFormVisible3" title="修改密码">
    <el-form :model="passs">
      <el-form-item label="原密码" prop="pass1" :label-width="formLabelWidth">
        <el-input v-model="passs.pass1" type="password"  autocomplete="off" />
      </el-form-item>
    <el-form-item label="新密码" prop="pass2" :label-width="formLabelWidth">
        <el-input v-model="passs.pass2" type="password"  autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible3 = false">取消</el-button>
        <el-button type="primary" @click="pass()"
          >确认</el-button
        >
      </span>
    </template>
  </el-dialog>
  <!-- 修改弹窗 -->
  <el-dialog v-model="dialogFormVisible" width="50%" title="" center>
      <div style="width:55%;margin:0 0;margin:auto;">
    <el-form :model="user2">
      <el-form-item label="昵称" :label-width="formLabelWidth">
        <el-input v-model="user2.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="用户名" :label-width="formLabelWidth">
        <el-input v-model="user2.username" autocomplete="off"></el-input>
      </el-form-item>
     <el-form-item label="性别" prop="sex">
        <el-radio-group v-model="user2.sex">
          <el-radio label="男"></el-radio>
          <el-radio label="女"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="邮箱" :label-width="formLabelWidth">
        <el-input v-model="user2.email" autocomplete="off"></el-input>
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
</div>
</template>

<style scoped>
.el-input{
  width: 300px;
}
</style>
<script scoped>
import axioss from '../../utils/axios'
import formatDate from '../../utils/time'
export default {
  name: 'userperson',
  data() {
      return {
         loading:true,
          dialogFormVisible:false,
          user:{},
          user2:{},
          passs:{
            id:'',
            pass1:'',
            pass2:''
          },
          pic:"http://localhost:9090/pic/upload/",
          pics:"",

          fileList:[],
          resume:'http://localhost:9090/resume/upload/',
          resume2:"",
          resume3:"http://localhost:9090/resume/d",
          dialogVisible:false,
          dialogFormVisible3:false,
          
      }
  },
  created(){
       this.load()
  },
  computed:{
    formatDate(){
        return formatDate;
      }
  },
  methods: {
      load(){
         this.loading=true
          let userStr = sessionStorage.getItem("user") || "{}"
          this.user = JSON.parse(userStr)
          this.user2 = this.user
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
                      if(this.user.resume==""||this.user.resume==null){
                        this.$message({
                        type: "success",
                        message:"请完善简历信息！！！"
                          })
                      }else{
                        if(this.fileList==""||this.fileList==null){
                        this.fileList.push({"url":this.resume3})
                        }else{
                          this.fileList[0]={"url":this.resume3}
                        }
              
                        
                      }
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
      up(){
      this.load()
      },
      // 下载
      handlePictureCardPreview(){
  
        window.location.href=this.user.resume
      },
      handleRemove(){
        axioss.post("/resume/dd/"+this.user.id).then(res=>{
          if(res.data.code=="0"){
            this.$message({
                        type: "success",
                        message:"成功"
                    })
          }
        })
      },
      pass(){
        this.passs.id =  this.user.id
        axioss.post("/user/pass",this.passs).then(res=>{
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
        this.dialogFormVisible3=false
      }

  },
  
}
</script>