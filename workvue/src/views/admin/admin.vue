<template>
  <el-container>
    <el-header>后台管理
      <div  style="float:right;padding-right:50px;">
    <el-dropdown>
      <span v-if="user.name"  class="aab" type="text" >
      <h4>管理员:{{user.username}}</h4>
    </span>
    <span v-else  class="aab" type="text" >
      <h4>管理员</h4>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item @click="dialogFormVisible = true">修改密码</el-dropdown-item>
        <el-dropdown-item @click="quit()">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
    </div>
    </el-header>
   <!-- 修改密码 -->
  <el-dialog v-model="dialogFormVisible" title="修改密码">
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
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="pass()"
          >确认</el-button
        >
      </span>
    </template>
  </el-dialog>
    <el-container style="width:100%;height:100vh;margin:0 0;margin:auto;">
      <el-aside width="200px" style="background-color:white;">
            <el-menu
             class="el-menu-vertical-demo"
                style="width: 200px;"
               
           
            :default-active="this.$route.path"
                router
               
            >
              <!-- <el-menu-item index="adminuser">
                <el-icon><icon-menu /></el-icon>
                用户管理
              </el-menu-item>
              <el-menu-item index="admintemp">
                <el-icon><icon-menu /></el-icon>
                <span>审核管理</span>
              </el-menu-item>
              <el-menu-item index="adminposition">
                <el-icon><icon-menu /></el-icon>
                <span>发布管理</span>
              </el-menu-item>
              <el-menu-item index="adminwork">
                <el-icon><icon-menu /></el-icon>
                <span>工作管理</span>
              </el-menu-item>
              <el-menu-item index="admincom">
                <el-icon><icon-menu /></el-icon>
                <span>完成管理</span>
              </el-menu-item>
              <el-menu-item index="admingive0">
                <el-icon><icon-menu /></el-icon>
                <span>终止审核</span>
              </el-menu-item>
              <el-menu-item index="admingive">
                <el-icon><icon-menu /></el-icon>
                <span>终止管理</span>
              </el-menu-item> -->

               <el-menu-item v-for="(l,key) in list" :key="key" :index="l.path"><el-icon><message /></el-icon>{{l.name}}</el-menu-item>
            </el-menu>
      </el-aside>
      <el-main style="background-color:white;">
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>
 
<script scoped>
import axioss from '../../utils/axios';

export default {
  name: "admin",
  data(){
    return{
          list:[
              {path:"/adminuser",name:" 用户管理"},
              {path:"/admintemp",name:"审核管理"},
              {path:"/adminposition",name:"发布管理"},
              {path:"/adminwork",name:"工作管理"},
              {path:"/admincom",name:"完成管理"},
              {path:"/admingive0",name:"终止审核"},
              {path:"/admingive",name:"终止管理"},
          ],
      user:{},
      form:{},
      dialogFormVisible:false,
       passs:{
            id:'',
            pass1:'',
            pass2:''
          },
    }
  },
  created(){
    this.load()
  },
  methods: {
    load(){
       let userStr = sessionStorage.getItem("user");
       this.user = JSON.parse(userStr);
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
      },
    quit(){
       sessionStorage.clear()
       this.$router.push('/login')
    },
  },
}
</script>

<style scoped>
.el-container { height: 100%; } 
.router-link-active {
  text-decoration: none;
}
.el-header{
  background-color: #b3c0d1;
  color: var(--el-text-color-primary);
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #d3dce6;
  color: var(--el-text-color-primary);
  text-align: center;
  line-height: 200px;
}
.aab{
  color: black;
}
.aab:hover{
  color: rgb(13, 167, 214);
}
.el-input{
  width: 300px;
}
</style>