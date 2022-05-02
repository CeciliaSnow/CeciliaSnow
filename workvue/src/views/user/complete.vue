<template>
  <ul v-if="position!=''" v-infinite-scroll="load" class="infinite-list" style="overflow: auto">
    <li v-for="(p,key) in position" :key="key" @click="info(key)"  class="infinite-list-item">
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
  <!-- 详情弹窗 -->
  <el-dialog v-model="dialogFormVisible1" title="">
  <div style="float:right;padding-right:50px">
     <el-button type="success">已完成</el-button>
     </div>
      <h1>{{position2.title}}</h1>
      <h3>类别:{{position2.category}}</h3>
      <h3>佣金:{{position2.money}}</h3>
      <h3>期限:{{position2.time}}</h3>
      <h4>任务需求</h4>
       <div v-html="position2.content"></div>
      <hr>
      <h3>雇主</h3>
      <p>{{beuser.name}}</p>
    <template #footer>
      <span class="dialog-footer">
        <el-button style="float:left" type="primary" @click="dell(position2.id)">删除</el-button>
        <el-button @click="dialogFormVisible1 = false;">取消</el-button>
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
</style>
<script>
import axioss from "../../utils/axios";

export default {
  name: "complete",
  data() {
    return {
    dialogFormVisible1:false,
      user: {},
      position: [],
      position2:{},
      beuser:{},
    };
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      let userStr = sessionStorage.getItem("user") || "{}";
      this.user = JSON.parse(userStr);
      axioss.post("/work/complete/" + this.user.id).then((res) => {
        if (res.data.code === "0") {
        this.position = res.data.data;
        } else {
          this.$message({
            type: "error",
            message: "失败",
          })
        }
      })
    },
    info(key){
    this.position2 = this.position[key]
    if(this.position2.userid){
            axioss.post('/user/querybyid/'+this.position2.userid).then(res=>{
                if (res.data.code === "0") {
                    this.beuser = res.data.data;
                    } else {
                    this.$message({
                        type: "error",
                        message: "失败",
                    });
                }
          })
        }
    this.dialogFormVisible1=true
    },
    // 删除
    dell(id){
        axioss.get("/work/admindel/"+id).then(res =>{
           if(res.data.code === "0"){
             this.dialogFormVisible1=false
              this.$message({
            type: "success",
            message:"删除成功"
            })
            this.load()
           }else{
              this.$message({
                type: "error",
                message:"删除失败"
              })
           }
        })
    },
  },
};
</script>