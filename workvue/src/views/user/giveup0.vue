<template>
  
  <ul v-if="position!=''" v-infinite-scroll="load" class="infinite-list" style="overflow: auto">
    <li v-for="(p,key) in position" :key="key" @click="info(key)"  class="infinite-list-item">
    <div>
      <h5 style="float:right;padding-right:30px">审核中。。。</h5>
    <h3>{{p.title}}</h3>
    <h5>类别:&nbsp;{{p.category}}&nbsp;&nbsp;&nbsp;
        佣金:&nbsp;{{p.money}}元&nbsp;&nbsp;&nbsp;
        期限:&nbsp;{{p.time}}天&nbsp;&nbsp;
    </h5>
    </div>
    </li>
  </ul>
  <div v-else class="bbbb"></div>
  <!-- 详情弹窗 -->
  <el-dialog v-model="dd" title="详情">
   <div style="margin-left:50px;">
  <h3>理由:&nbsp;&nbsp;&nbsp;{{positioninfo.msg}}</h3>
  <h1>标题:&nbsp;&nbsp;&nbsp;{{positioninfo.title}}</h1>
  <h3>发布人:&nbsp;&nbsp;&nbsp;{{positioninfo.name1}}</h3>
  <h4>类别:&nbsp;&nbsp;&nbsp;{{positioninfo.category}}</h4>
  <h4>佣金:&nbsp;&nbsp;&nbsp;{{positioninfo.money}}元</h4>
  <h4>期限:&nbsp;&nbsp;&nbsp;{{positioninfo.time}}天</h4>
  <h4>接取人数:&nbsp;&nbsp;&nbsp;{{positioninfo.num}}</h4>
  <h4>负责人:&nbsp;&nbsp;&nbsp;{{positioninfo.people}}</h4>
  <h4>联系方式:&nbsp;&nbsp;&nbsp;{{positioninfo.phone}}</h4>
  <h4>联系邮箱:&nbsp;&nbsp;&nbsp;{{positioninfo.email}}</h4>
  <h4>详情</h4>
      <div v-html="positioninfo.content"></div>
   </div>
  <template #footer>
      <span class="dialog-footer">
        <el-button @click="dd = false">取消</el-button>
        <el-button type="primary" @click="dd = false;del()">继续任务</el-button>
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
<script scoped>
import axioss from "../../utils/axios";
import router from "@/router";
export default {
  name: "giveup0",
  data() {
    return {
      user: {},
      position: [],
      employer:{},
      dd:false,
      positioninfo:{}
    };
  },
  created() {
    this.load();
  },
  methods: {
    load() {
        // 任务信息
      let userStr = sessionStorage.getItem("user") || "{}";
      this.user = JSON.parse(userStr);
      axioss.get("/work/fangqis1/" + this.user.id).then(res => {
        if (res.data.code === "0") {
          this.position = res.data.data;
        } else {
          this.$message({
            type: "error",
            message: "失败",
          });
        }
      });
    },
    info(key){
       if(this.user.id){
          this.dd = true
          this.positioninfo = this.position[key]
          }
    },
    del(){
      axioss.post("/work/fangqitz/"+this.positioninfo.id).then(res=>{
        if(res.data.code==="0"){
          router.push('/work')
           this.$message({
            type: "success",
            message: "成功",
          });
        }
      })
    }
  },
};
</script>