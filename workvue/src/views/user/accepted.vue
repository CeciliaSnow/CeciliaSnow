<template>
  
  <ul v-if="position!=''" v-infinite-scroll="load" class="infinite-list" style="overflow: auto">
    <li v-for="(p,key) in position" :key="key" @click="info(p.id)"  class="infinite-list-item">
    <div>
    <h3>{{p.title}}</h3>
    <h5>类别:&nbsp;{{p.category}}&nbsp;&nbsp;
        佣金:&nbsp;{{p.money}}元&nbsp;&nbsp;
        期限:&nbsp;{{p.time}}天&nbsp;&nbsp;
    </h5>
    </div>
    </li>
  </ul>
<div v-else class="bbbb">
</div>
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
  name: "accepted",
  data() {
    return {
      user: {},
      position: [],
    };
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      let userStr = sessionStorage.getItem("user") || "{}";
      this.user = JSON.parse(userStr);
      axioss.post("/position/accepted/"+this.user.id).then((res) => {
        if (res.data.code === "0") {
          this.position = res.data.data;
          console.log(res.data.data)
        } else {
          this.$message({
            type: "error",
            message: "失败",
          });
        }
      });
      
    },
    info(key){
      this.$router.push('/accepted2/'+key)
    }
  },
};
</script>