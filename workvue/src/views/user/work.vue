<template>
  
  <ul v-if="position!=''" v-infinite-scroll="load" class="infinite-list" style="overflow: auto">
    <li v-for="(p,key) in position" :key="key" @click="info(p.id)"  class="infinite-list-item">
    <div>
    <h3>{{p.title}}</h3>
    <h5>负责人:&nbsp;{{p.people}}&nbsp;&nbsp;&nbsp;
        联系方式:&nbsp;{{p.phone}}&nbsp;&nbsp;&nbsp;
        联系邮箱:&nbsp;{{p.email}}&nbsp;&nbsp;
    </h5>
    </div>
    </li>
  </ul>
  <div v-else class="bbbb"></div>
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
  name: "work",
  data() {
    return {
      user: {},
      position: [],
      employer:{}
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
      axioss.post("/work/work/" + this.user.id).then((res) => {
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
       this.$router.push('/work2/'+key)
    }
  },
};
</script>