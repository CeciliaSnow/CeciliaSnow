<template>
  <div class="index">
      <div v-loading="loading" class="index-left" style="flex:3;padding-left:30px">
        <div style="border-bottom: 1px solid #e9ecee">
          <div style="float:right;padding-right:50px">
          <el-button  @click=" this.$router.push('/employ')" type="primary">返回</el-button>
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
        <br>
        <hr/>
        <div style="float:right;padding-right:50px">
        <el-button type="text" @click="dialogFormVisible=true">负责人信息</el-button><br/>
        <el-button type="text" @click="dialogFormVisible2=true">更改负责人信息</el-button><br/>
        </div>
         <h1>执行人</h1>
         <h3>{{user.name}}</h3>
            <el-button type="text" @click="fdown()">放弃任务</el-button>
         <div style="float:right;padding-right:50px">
                <el-popconfirm
                  confirm-button-text="Yes"
                  cancel-button-text="No"
                  :icon="InfoFilled"
                  icon-color="red"
                  title="是否确定任务完成?"
                  @confirm="complete(position.id)"
                  @cancel="cancelEvent"
                >
                  <template #reference>
                     <el-button type="primary" plain>任务完成</el-button>
                  </template>
                </el-popconfirm>
         </div>
      </div>
      <el-dialog v-model="dialogFormVisible" title="">
         <h3>负责人:{{position.people}}</h3>
         <h3>联系方式:{{position.phone}}</h3>
         <h3>联系邮箱:{{position.email}}</h3>
         <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
      </span>
    </template>
  </el-dialog>
  <el-dialog v-model="dialogFormVisible3" title="终止任务">
    <el-form :model="position">
      <el-form-item label="终止理由" :label-width="formLabelWidth">
        <el-input v-model="position.msg" autocomplete="off" />
      </el-form-item>
     
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible3 = false">取消</el-button>
        <el-button type="primary" @click="que()"
          >确认</el-button
        >
      </span>
    </template>
  </el-dialog>
  <!-- 修改弹窗 -->
  <el-dialog v-model="dialogFormVisible2" width="50%" title="" center>
      <div style="width:55%;margin:0 0;margin:auto;">
    <el-form :model="user">
      <el-form-item label="负责人" :label-width="formLabelWidth">
        <el-input v-model="position.people" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="联系方式" :label-width="formLabelWidth">
        <el-input v-model="position.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" :label-width="formLabelWidth">
        <el-input v-model="position.email" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible2 = false">取消</el-button>
        <el-button type="primary" @click="genggai()">确认</el-button>
      </span>
    </template>
  </el-dialog>
      <div class="index-right"  style="flex:1;border:30px solid #f6f8f9;">
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
<script scoped>
import axioss from '../../utils/axios'
export default {
  name: 'employ2',
  data() {
      return {
        loading:true,
          key:'',
          position:{},
          dialogFormVisible:false,
          dialogFormVisible2:false,
           dialogFormVisible3:false,
          user:{},
          upload:"http://localhost:9090/fabufile/upload/",
          order:{},
          downs:"http://localhost:9090/files/down/"
      }
  },
  created(){
      this.load()
  },
  methods: {
      load(){
        this.loading=true
          this.key = this.$route.params.key
          this.upload=this.upload+this.key
          this.downs=this.downs+this.key
          axioss.post("/work/bework2/"+this.key).then(res=>{
              if(res.data.code ==='0'){
            this.position = res.data.data
            this.user = res.data.data2
            document.getElementById("zhi").innerHTML=this.position.content
            }
            this.loading=false
      })
      
      },
      
      genggai(){
        axioss.post("/work/genggai",this.position).then(res=>{
            this.dialogFormVisible2 = false
        })
      },
      fdown(){
        this.dialogFormVisible3=true
      },
      que(){
        axioss.post("/work/fangqi2",this.position).then(res=>{
                  if(res.data.code==="0"){
                  this.$router.push("/giveups0")
                  this.$message({
                    type:"success",
                    message:"等待审核中。。"
                  })
                  }
                })
        dialogFormVisible3 = false
      },
      complete(key){
        axioss.post("/work/com/"+this.key).then(res=>{
          if(res.data.code=="0"){
            this.$router.push("/complete2")
              this.$message({
              type:"success",
              message:"任务已完成"
            })
          }
        })
      },
  }
}
</script>