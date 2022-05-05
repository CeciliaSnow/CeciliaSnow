<template>
    <div class="quan">
        <div class="zhu"></div>
        <div class="fu">
            <div class="form"><br>
                <h2 style="margin-left:20px">登录兼职社区</h2><br/>
                <div style="margin-left:20px">
                    <el-select v-model="value" class="m-2" placeholder="个人登录" size="large">
                    <el-option
                    label="用户登录"
                    value="person"
                    @click="xuan(1)"
                    />
                    <el-option
                    label="管理员"
                    value="qiye"
                    @click="xuan(2)"
                    />
                    </el-select>
                 </div><br/><br/>
            <div v-if="value=='person'">
                     <el-form
                        ref="ruleFormRef"
                        :model="ruleForm"
                        :rules="rules"
                        label-width="160px"
                        class="demo-ruleForm"
                        size="large"
                    >
                        <el-form-item label="用户名" prop="username">
                        <el-input v-model="ruleForm.username" />
                        </el-form-item>
                        <el-form-item label="密码" prop="password">
                        <el-input v-model="ruleForm.password" type="password" autocomplete="off" />
                        </el-form-item>
                        </el-form>
                        <br/><br/>
                        <div style="display:flex;width:300px;margin:0 0;margin:auto;">
                    <el-button style="flex:1" type="primary" size="large" @click="geren()" round>注册</el-button>
                    <el-button style="flex:1" type="primary" size="large" @click="login()" round>登录</el-button>
                    </div>
                    </div>
                        <div v-else>
                        <el-form
                        ref="ruleFormRef"
                        :model="ruleForm2"
                        :rules="rules"
                        label-width="160px"
                        class="demo-ruleForm"
                        size="large"
                    >
                        <el-form-item label="管理账户" prop="username">
                        <el-input v-model="ruleForm2.username" />
                        </el-form-item>
                        <el-form-item label="密码" prop="password">
                        <el-input v-model="ruleForm2.password" type="password" autocomplete="off" />
                        </el-form-item>
                        </el-form>
                        <br/><br/>
                    <div style="display:flex;width:300px;margin:0 0;margin:auto;">
                    <el-button style="flex:1" type="primary" size="large" @click="centerDialogVisible='true'" round>注册</el-button>
                    <el-button style="flex:1" type="primary" size="large" @click="login2()" round>登录</el-button>
                    </div>
                    </div>
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
    flex:2.5;
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
<script scoped>
import axioss from '../utils/axios'
export default {
  name:"login",
  data() {
    return {
      value:'person',
      ruleForm: {
        username: '',
        password: '',
      },
     ruleForm2: {
        username: '',
        password: '',
      },
    }
  },
  created() {
    this.load(1);
  },
  computed: {
    error () {
      return this.username.trim().length < 7
        ? 'Please enter a longer username'
        : ''
    }
  },
  methods: {
    load(r){
      let userStr = sessionStorage.getItem("user") || "{}";
      this.user = JSON.parse(userStr);
      if(this.user){
        if(this.user.role==1){
          this.$router.push('/person')
        }else if(this.user.role==2){
          this.$router.push('/person2')
        } 
      }
      if(r==1){
        this.value="person"
      }else{
        this.value="qiye"
      }
    },
    xuan(r){
      this.load(r)
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },

    geren(){
      this.$router.push('/register')
    },
    login(){
      if(this.ruleForm.username!="admin"&&this.ruleForm.password!="admin"){
          axioss.post('/user/login',this.ruleForm).then(res=>{
              if(res.data.code === '0'){
                this.$message({
                  type:"success",
                  message:"登录成功"
                })
                sessionStorage.setItem("user",JSON.stringify(res.data.data))
                if(res.data.data.role==1){
                  this.$router.push("/person")
                }else{
                  this.$router.push("/person2")
                }
                
              }else{
                this.$message({
                  type:"error",
                  message: res.data.msg
                })
              }
          })
      }else{
         this.$message({
                  type:"error",
                  message: "此账号最好不要登录用户！！！"
                })
      }
    },
    login2(){
      axioss.post('/user/adminlogin',this.ruleForm2).then(res=>{
          if(res.data.code === '0'){
            if(res.data.data.role==3){
              sessionStorage.setItem("user",JSON.stringify(res.data.data))
              this.$message({
              type:"success",
              message: '登录成功！'
            })
               this.$router.push("/adminuser")
            }else{
              this.$message({
              type:"error",
              message: '用户名或密码错误！'
            })
            this.load()
            }
          }else{
            this.$message({
              type:"error",
              message: res.data.msg
            })
          }
      })
    }
  },
}
</script>