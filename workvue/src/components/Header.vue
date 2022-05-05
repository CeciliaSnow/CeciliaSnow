<template>
  <div class="aa" style="height: 70px;line-height: 70px;border-bottom: 1px solid #ccc;display: flex">
    <div style="width: 200px;padding-left: 30px;font-weight: bold;font-size: 30px;color: deepskyblue">兼职社区</div>
    <div style="flex: 1;display:flex;">
      <div style="flex:1">
      <el-input
      style="width:200px;"
      v-model="se"
      size="large"
      placeholder="请输入标题"
      :prefix-icon="Search"
    ></el-input>&nbsp;
      <el-button type="primary" size="large" style="width:80px" @click="searchs()" :icon="Search">搜索</el-button>
      </div>
    </div>
    <div style="width: 100px" @click="$router.push('/')"><el-button class="aab" type="text">首页</el-button></div>
    <div style="width: 100px">
      <el-button v-if="user.role==2" class="aab" @click="work()" type="text">雇佣管理</el-button>
      <el-button v-else class="aab" @click="work()" type="text">工作管理</el-button></div>
    <div style="width: 100px" v-if="flag==0"><el-button type="text">我要找兼职</el-button></div>
    <div style="width: 100px" v-else><el-button type="text" @click="juese()">切换身份</el-button></div>
    <div style="width: 100px" v-if="radio==2"><el-button type="text" @click="fabu3()">发布兼职</el-button></div>
    <div v-if="user.name"  style="width: 100px">
    <el-dropdown>
    <span  class="aab"  v-if="pics==''||pics==null" type="text" >
      {{user.name}}
    </span>
    <span class="block" v-else>
          <el-avatar :size="small" :src="pics" style="margin-top:6px;"></el-avatar>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item @click="name()">个人用户</el-dropdown-item>
        <el-dropdown-item @click="quit()">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
    </div>
    <div v-else @click="$router.push('/login')" style="width: 100px"><el-button type="text">登录</el-button></div>
    
  </div>
  <!-- 选角色弹窗 -->
  <el-dialog v-model="centerDialogVisible" title="请选择您的账户" width="50%" center>
    <div>
       <el-radio-group v-model="radio" size="large">
    <el-radio label="2" @click="juese2()" border>招聘账户</el-radio>
    <el-radio label="1"  @click="juese1()" border>工作者账户</el-radio>
      </el-radio-group>
  </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="centerDialogVisible = false">返回</el-button>
      </span>
    </template>
  </el-dialog>
  <!-- 发布弹窗 -->
<el-dialog v-model="dia" title="">
    <el-form :model="formh">
      <el-form-item label="标题" :label-width="formLabelWidth">
        <el-input v-model="formh.title" autocomplete="off" style="width:35%"></el-input>
      </el-form-item>
      <el-form-item label="类别" :label-width="formLabelWidth">
        <el-select v-model="formh.category" placeholder="请选择类别">
          <el-option label="设计" value="设计"></el-option>
          <el-option label="文案写作" value="文案写作"></el-option>
          <el-option label="技术" value="技术"></el-option>
          <el-option label="音视频" value="音视频"></el-option>
          <el-option label="运营" value="运营"></el-option>
          <el-option label="市场营销" value="市场营销"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="雇佣金" :label-width="formLabelWidth">
        <el-input v-model="formh.money" autocomplete="off" style="width:15%"></el-input>CNY
      </el-form-item>
      <el-form-item label="期限" :label-width="formLabelWidth">
        <el-input v-model="formh.time" autocomplete="off" style="width:15%"></el-input>天
      </el-form-item>
        <el-form-item label="负责人" :label-width="formLabelWidth">
        <el-input v-model="formh.people" autocomplete="off" style="width:20%"></el-input>
      </el-form-item>
      <el-form-item label="联系方式" :label-width="formLabelWidth">
        <el-input v-model="formh.phone" autocomplete="off" style="width:30%"></el-input>
      </el-form-item>
      <el-form-item label="联系邮箱" :label-width="formLabelWidth">
        <el-input v-model="formh.email" autocomplete="off" style="width:30%"></el-input>
      </el-form-item>
      <div id="div5"></div>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dia = false; qingkong()">取消</el-button>
        <el-button type="primary" @click="dia = false;add3()">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<style scoped>
*{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
.dialog-footer button:first-child {
  margin-right: 10px;
}
.aab{
  color: black;
}
.aab:hover{
  color: rgb(13, 167, 214);
}

</style>
<script scoped>
import E from 'wangeditor'
import axioss from '../utils/axios';
let editor;
export default {
  inject: ['reload'],
  name: "Header",
  data() {
    return {
      se:'',
      user:{},
      centerDialogVisible:false,
      dia:false,
      radio:'',
      flag:'',
      pics:'',
      formh:{}
    }
  },
  created(){
    this.load();
  },
  watch: {
    $route(to) {
      if (to.path != "/aa") {
      // 这里就是写页面跳转后需要做的逻辑处理,例如在页面跳转时清空上传的结果  
      this.load();
      }
    }
  },
  methods: {
    load(){
      let userStr = sessionStorage.getItem("user");
      if(userStr){
      this.user = JSON.parse(userStr);
      if(this.user&&this.user!=''&&this.user!=null){
        axioss.get("/user/querybyid/"+this.user.id).then(res=>{
          if(res.data.code==="0"){
                      sessionStorage.setItem("user",JSON.stringify(res.data.data))
                      let userStr = sessionStorage.getItem("user") || "{}"
                      this.user = JSON.parse(userStr)
                      this.radio=this.user.role
                      this.pics = this.user.pic
                }
        })
        this.flag=1;
      }
      }else{
        this.flag=0;
      }
    },
    searchs(){
  
       this.$store.commit('add',this.se) 
       
    },
    name(){
      if(this.user){
        if(this.user.role==1){
          this.$router.push('/person')
        }else if(this.user.role==2){
          this.$router.push('/person2')
        } 
      }else{
        this.$router.push('/login')
      }
    },
    quit(){
       sessionStorage.clear()
       this.$router.push('/login')
    },
    work(){
      let userStr = sessionStorage.getItem("user") || "{}";
      this.user = JSON.parse(userStr);
      if(this.user){
        axioss.post("/user/sel/"+this.user.id).then(res=>{
                  this.user = res.data.data
                  this.radio = this.user.role
                  if(this.radio=="1"){
                    this.$router.push('/work')
                  }else{
                    this.$router.push('/employ')
                  }
                });
      }else{
        this.$message({
            type: "success",
            message: "请登录",
          });
      }
    },
    juese(){
      if(this.user.role==1||this.user.role==2){
        let userStr = sessionStorage.getItem("user") || "{}";
        this.user = JSON.parse(userStr);
        if(this.user){
          axioss.post("/user/sel/"+this.user.id).then(res=>{
                    this.user = res.data.data
                    this.radio = this.user.role
                    this.centerDialogVisible=true
                  });
        }else{
          this.$message({
              type: "success",
              message: "请登录",
            });
        }
      }else{
        this.centerDialogVisible=false
      }
    },
    juese1(){
    if(this.user){
      this.radio = 1
      this.user.role = this.radio
      axioss.post("/user/role",this.user).then(res=>{
        if(res.data.code=="0"){
          this.centerDialogVisible=false
          this.$router.push('/')
        }else{
          this.$message({
            type: "error",
            message: "失败"
          });
        }
      })
    }
    },
    juese2(){
      if(this.user){
        this.radio = 2
        this.user.role = this.radio
        axioss.post("/user/role",this.user).then(res=>{
          if(res.data.code=="0"){
            this.centerDialogVisible=false
            this.$router.push('/person2')
          }else{
            this.$message({
              type: "error",
              message: "失败"
            });
          }
        })
      }
    },
    qingkong(){editor.txt.clear()},
    fabu3(){
          if(this.user.id){
            this.dia = true
            // 异步加载处理
            this.$nextTick(()=>{
              if(editor!=null){
                editor.destroy()
              }
                editor = new E('#div5')
                // 设置富文本高度
                editor.config.height = 200
                editor.create()
                this.qingkong()
            })
          }else{
          }
           
        },
    add3(){
            if(this.user.id){
                // 获取文本
                this.formh.userid = this.user.id
                this.formh.name = this.user.name
                this.formh.content = editor.txt.html()
                axioss.post("/temp/addtemp",this.formh).then(res=>{
                  if(res.data.code === "0"){
                    this.qingkong()
                     location.reload()
                     this.$message({
                      type:"success",
                      message: "审核中。。"
                    })
                  }else{
                    this.$message({
                      type:"error",
                      message: "添加失败"
                    })
                  }
                })

            }else{
               this.$message({
                      type:"error",
                      message: "请登录"
                    })
            }
        },
  },
}
</script>

