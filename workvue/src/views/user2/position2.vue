<template>
  <el-button type="text" round style="float:right;" @click="fabu">发布</el-button>
<ul v-if="position!=''" v-infinite-scroll="load" class="infinite-list" style="overflow: auto">
    <li v-for="(p,key) in position" :key="key" @click="info(key)" class="infinite-list-item">
    <div>
    <h3>{{p.title}}</h3>
    <h5>类别:&nbsp;{{p.category}}&nbsp;&nbsp;
        佣金:&nbsp;{{p.money}}元&nbsp;&nbsp;
        期限:&nbsp;{{p.time}}天&nbsp;&nbsp;
    </h5>
    </div>
    </li>
  </ul>
  <div v-else class="bbbb"></div>
<!-- 发布弹窗 -->
<el-dialog v-model="dialogFormVisible" title="">
    <el-form :model="form">
      <el-form-item label="标题" :label-width="formLabelWidth">
        <el-input v-model="form.title" autocomplete="off" style="width:35%"></el-input>
      </el-form-item>
      <el-form-item label="类别" :label-width="formLabelWidth">
        <el-select v-model="form.category" placeholder="请选择类别">
          <el-option label="设计" value="设计"></el-option>
          <el-option label="文案写作" value="文案写作"></el-option>
          <el-option label="技术" value="技术"></el-option>
          <el-option label="音视频" value="音视频"></el-option>
          <el-option label="运营" value="运营"></el-option>
          <el-option label="市场营销" value="市场营销"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="雇佣金" :label-width="formLabelWidth">
        <el-input v-model="form.money" autocomplete="off" style="width:15%"></el-input>CNY
      </el-form-item>
      <el-form-item label="期限" :label-width="formLabelWidth">
        <el-input v-model="form.time" autocomplete="off" style="width:15%"></el-input>天
      </el-form-item>
      <el-form-item label="负责人" :label-width="formLabelWidth">
        <el-input v-model="form.people" autocomplete="off" style="width:20%"></el-input>
      </el-form-item>
      <el-form-item label="联系方式" :label-width="formLabelWidth">
        <el-input v-model="form.phone" autocomplete="off" style="width:30%"></el-input>
      </el-form-item>
      <el-form-item label="联系邮箱" :label-width="formLabelWidth">
        <el-input v-model="form.email" autocomplete="off" style="width:30%"></el-input>
      </el-form-item>
      <div id="div1"></div>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false; qingkong()">取消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false;add()">确认</el-button>
      </span>
    </template>
  </el-dialog>
  <!-- 详情弹窗 -->
  <el-dialog v-model="dialogFormVisible1" title="">
    <el-form :model="positioninfo">
      <el-form-item label="标题" :label-width="formLabelWidth">
        <el-input v-model="positioninfo.title" autocomplete="off" style="width:35%"></el-input>
      </el-form-item>
      <el-form-item label="类别" :label-width="formLabelWidth">
        <el-select v-model="positioninfo.category" placeholder="请选择类别">
          <el-option label="Zone No.1" value="shanghai"></el-option>
          <el-option label="Zone No.2" value="beijing"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="雇佣金" :label-width="formLabelWidth">
        <el-input v-model="positioninfo.money" autocomplete="off" style="width:15%"></el-input>CNY
      </el-form-item>
      <el-form-item label="期限" :label-width="formLabelWidth">
        <el-input v-model="positioninfo.time" autocomplete="off" style="width:15%"></el-input>天
      </el-form-item>
      <el-form-item label="负责人" :label-width="formLabelWidth">
        <el-input v-model="positioninfo.people" autocomplete="off" style="width:15%"></el-input>
      </el-form-item>
      <el-form-item label="联系方式" :label-width="formLabelWidth">
        <el-input v-model="positioninfo.phone" autocomplete="off" ></el-input>
      </el-form-item>
      <el-form-item label="联系邮箱" :label-width="formLabelWidth">
        <el-input v-model="positioninfo.email" autocomplete="off" ></el-input>
      </el-form-item>
      <div id="div1"></div>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false; qingkong()">取消</el-button>
        <el-button style="float:left" type="primary" @click="dell(positioninfo.id)">取消发布</el-button>
        <el-button type="primary" @click="infod()">重新发布</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.infinite-list {
  
  padding: 0;
  margin: 0;
  list-style: none;
}
.infinite-list-item{
   height: 80px;
   border-bottom: 1px solid #e9ecee;
}
.infinite-list-item:hover{
  background-color: rgb(242,249,254);
}
.bbbb{
  width: 60%;
  height: 65%;
   background-image: url('../../assets/pic/wu.png');
    background-repeat:no-repeat;
    background-position:0% 0%;
    background-size: cover;
    margin: 0 0;
    margin: auto;
}
.el-input{
  width: 300px;
}
</style>
<script scoped>
import E from 'wangeditor'
import axioss from '../../utils/axios';
// 设置全局变量
let editor;
export default {
  name: 'position2',
  data() {
      return {
        dialogFormVisible:false,
        dialogFormVisible1:false,
        formLabelWidth :'',
        size: '',
        form:{},
        position:[],
        positioninfo:{}
      }
    },
    created() {
      this.load()
    },
    methods: {
        load(){
          let userStr = sessionStorage.getItem("user") || "{}"
          this.user = JSON.parse(userStr)
          if(this.user.id){
                axioss.post("/position/queryposition/"+this.user.id).then(res=>{
                  if(res.data.code === "0"){
                     this.position = res.data.data
                     this.form.userid = this.user.id
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
        qingkong(){editor.txt.clear()},
        fabu(){
            this.dialogFormVisible = true
            this.form={}
            // 异步加载处理
            this.$nextTick(()=>{
                if(editor!=null){
                editor.destroy()
              }
                editor = new E('#div1')
                // 设置富文本高度
                editor.config.height = 200
                editor.create()
                this.qingkong()
            })
           
        },
        add(){
            this.qingkong()
                // 获取文本
                this.form.content = editor.txt.html()
                axioss.post("/temp/addtemp",this.form).then(res=>{
                  if(res.data.code === "0"){
                    this.load()
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
        },
        info(key){
          if(this.user.id){
          this.dialogFormVisible1 = true
          this.positioninfo = this.position[key]
            this.$nextTick(()=>{
              if(editor!=null){
                editor.destroy()
              }
                editor = new E('#div1')
                // 设置富文本高度
                editor.config.height = 200
                editor.create()
                editor.txt.append(this.positioninfo.content)
            })
          }
        },
        infod(){
            this.positioninfo.content = editor.txt.html()
            console.log(this.positioninfo.content)
            this.dialogFormVisible1 = false
            axioss.post("/position/update",this.positioninfo).then(res=>{
              if(res.data.code=="0"){
                this.load()
                this.$message({
                      type:"success",
                      message: "成功"
                    })
                    this.qingkong()
              }else{
                this.$message({
                      type:"error",
                      message: "失败"
                    })
              }
            })
        },
        dell(id){
           this.dialogFormVisible1 = false
           axioss.get('/position/no/'+id).then(res=>{
            if(res.data.code === "0"){
                this.load()
                this.$message({
                type: "success",
                message:"成功"
              })
            }else{
              this.$message({
                type: "error",
                message:"失败"
              })
            }
        })
        }
    },
}
</script>