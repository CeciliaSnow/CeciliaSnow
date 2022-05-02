<template>
    <div class="quan">
        <div class="zhu"></div>
        <div class="fu">
            <div class="form"><br>
                <h2 style="margin-left:20px">注册兼职社区</h2><br/>
                
    <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="180px"
        class="demo-ruleForm"
    >
      <el-form-item label="昵称" prop="name">
        <el-input v-model="ruleForm.name"></el-input>
      </el-form-item>

      <el-form-item label="用户名" prop="username">
        <el-input v-model="ruleForm.username"></el-input>
      </el-form-item>

      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="ruleForm.password"></el-input>
      </el-form-item>

      <el-form-item label="性别" prop="sex">
        <el-radio-group v-model="ruleForm.sex">
          <el-radio label="男"></el-radio>
          <el-radio label="女"></el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item prop="email" label="邮箱">
        <el-input v-model="ruleForm.email"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="register()">注册</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
        <router-link class="quit" to="/login"><el-button>返回</el-button></router-link>
      </el-form-item>
    </el-form><br/><br/>           
            </div>
        </div>
    </div>
</template>
<style scoped>
.quan{
    margin: 0;
    padding: 0;
    display: flex;
    width: 100vw;
    height: 100vh;
   background-color: #f6f8f9;
}
.zhu{
    flex: 1;
    height: 100vh;
    background-image: url("../assets/pic/b.jpg");
    background-repeat:no-repeat;
    background-position:0% 0%;
    background-size: cover;
}
.fu{
    flex:2;
    height: 100vh;
    background-image: url('../assets/pic/bc.jpg');
    background-repeat:no-repeat;
    background-position:0% 0%;
    background-size: cover;
}
.form{
    width: 60%;
    height: 60vh;
    margin: 0;
    margin: auto;
    margin-top: 15vh;
    background-color: white;
}
.demo-ruleForm{
  left: 0px;
  right: 0px;
  margin: auto;
}
.el-input{
  width: 300px;
}
</style>
<script>
import axioss from "../utils/axios";

export default {
  name: "register",
  data(){
    return {
      key:'',
      centerDialogVisible:false,
      ruleForm: {
        name: '',
        username: '',
        password: '',
        sex: '',
        email: '',
        radio:''
        
      },
      rules: {
        name: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          {
            min: 2,
            max: 5,
            message: '长度在 2 到 5 个字符',
            trigger: 'blur',
          },
        ],
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          {
            min: 3,
            max: 5,
            message: '长度在 3 到 5 个字符',
            trigger: 'blur',
          },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          {
            min: 3,
            max: 5,
            message: '长度在 3 到 5 个字符',
            trigger: 'blur',
          },
        ],
        birth: [
          {
            type: 'date',
            required: true,
            message: '请选择日期',
            trigger: 'change',
          },
        ],
        email:[
            {
              required: true,
              message: '请输入邮箱地址',
              trigger: 'blur'
            },
            {
              type: 'email',
              message: '请输入正确的邮箱地址',
              trigger: 'blur'
            }
          ]


      },
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      // 判断登录
      let userStr = sessionStorage.getItem("user") || "{}";
      this.user = JSON.parse(userStr);
      if(this.user){
        if(this.user.role==1){
          this.$router.push('/person')
        }else if(this.user.role==2){
          this.$router.push('/person2')
        } 
      }
    },
    geren(){
      this.$router.push('/register/2')
       this.load()
       this.centerDialogVisible=false
    },
    register() {
      this.$refs['ruleForm'].validate((valid)=>{
        if(valid){
          axioss.post("/user/register",this.ruleForm).then(res =>{
            console.log(res)
            if(res.data.code==="0"){
              this.$message({
                type:"success",
                message:"注册成功"
              })
              this.$router.push("/login")
            }else{
              this.$message({
                type:"error",
                message: res.data.msg
              })
            }
          })
        }
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!')
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
  }
}
</script>