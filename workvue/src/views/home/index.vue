<template>
<div class="b">
  <div class="index" style="height:100vh;">
      <div class="left">
      <div style="height:80px;border-bottom: 1px solid #e9ecee;">
      <h2 class="zi" style="padding-left:50px;font-family: '黑体';font-size:30px;padding-top:25px;">热门工作</h2>
      </div>
        <div class="zhi" style="height:60px;display:flex;border-bottom: 1px solid #e9ecee;">
        <el-button type="text" disabled style="flex:1">职位类型</el-button>
        <el-button type="text" @click="s()" style="flex:1">首页</el-button>
        <el-button type="text" @click="fen(1)" style="flex:1">设计</el-button>
        <el-button type="text" @click="fen(2)" style="flex:1">文案写作</el-button>
        <el-button type="text" @click="fen(3)" style="flex:1">技术</el-button>
        <el-button type="text" @click="fen(4)" style="flex:1">音视频</el-button>
        <el-button type="text" @click="fen(5)" style="flex:1">运营</el-button>
        <el-button type="text" @click="fen(6)" style="flex:1">市场营销</el-button>
        </div>
        <div class="hh" v-loading="loading">
          <div class="h" v-for="p in position" :key="p.id" style="height:120px;display:flex" @click="po(p.id)">
                <div style="flex:1;margin:0 0;margin:auto;padding-left:50px">{{p.money}}元<br/>酬金</div>
                <div style="flex:2;margin:0;margin:auto;">{{p.title}}<br/>{{p.category}}---投递人数:{{p.num}}</div>
          </div>
          <div style="height:80px;
              text-align: center;
              background-color: white;
              ">
            <el-button type="text" v-if="page*page_count<length" @click="moreShow()">点击查询更多</el-button>
            <h4 type="text" style="margin:0 0;margin:auto;padding-top:20px" v-else disabled>已无更多</h4>
          </div>
        </div>
      </div>
      <div class="right">
        <br>
        <br>
        <div style="width:150px;left:0px;right:0px;margin:auto;">
        <img src="../../assets/pic/a.png" width="130" height="130" object-fit="cover"> 
        <h4 style="text-align:center;"></h4>
        <p style="font-size:10px;font-family:'楷体'"></p>
        <br>
       
        <img src="../../assets/pic/c.png" width="130" height="130" object-fit="cover">
        <h4 style="text-align:center;">公共诚信保障</h4>
        <p style="font-size:10px;font-family:'楷体'">兼职社区致力于维护双方真实的交易，保证双方利益与平台公平性</p>
        </div>
      </div>
  </div>
 
</div>

</template>

<style >
.b{
 width: 100%;
  height: 100vh;
 
}
.index {
    width: 70%;
    height: 100vh;
    left: 0px;
    right: 0px;
    margin: auto;
    display: flex;
    border: 0px;
}
.index>*{
  background: white;   
}
.left{
    flex: 3;   
}
.zhi>*{
  font-size: 15px;
  padding-top:25px;
}
.right{
    flex:1;
    border-left: 30px solid #f6f8f9;
}

.h{
  background-color: white;
  border-bottom: 1px solid #e9ecee;
}
.h:hover{
    background-color: rgb(242,249,254);
}
</style>
<script>
import Header from '../../components/Header.vue'
import axioss from '../../utils/axios'
import router from "@/router";
export default {
  inject: ['reload'],
  name: 'index',
  components:{
    Header
  },
  data() {
    return {
      loading:true,
      moreShowBoolen: false,
      position:[],
      fenlei:'',

      page:1,
      page_count:10,
      length:1,
      se:"",
      l:1,
    }
  },
  created() {
    this.load();
  },
watch: {
  "$store.state.se":{
    handler:function(newVal,oldVal){
      this.se=newVal;
      this.load();
    }
  }
},
  methods: {
     s(){
    location.reload();
  },
    load(){
      axioss.get("/position/length",{params:{search:this.se}}).then(res=>{
           this.length=res.data.data.length
         }),
      axioss.get("/position/queryallposition",{
                params:{
                pageNum : this.page,//页数
                pageSize : this.page_count,//一页多少
                search: this.se
                }
              }).then(res=>{
                  if(res.data.code === "0"){
                      this.position = res.data.data.records
                      this.loading=false
                  }else{
                    this.$message({
                      type:"error",
                      message: "失败"
                    })
                  }
                })
        },
        // 点击查询更多
        moreShow:function(){
          this.page+=1;
          axioss.get("/position/queryallposition",{
              params:{
              pageNum : this.page,//页数
              pageSize : this.page_count,//一页多少
              }
              })
              .then(ret => {
                  this.position = this.position.concat(ret.data.data.records); //将请求回来的数据和上一次进行组合
              })
        },
    fen(m){
      axioss.get("/position/fenlei/"+m,{
              params:{
              pageNum : this.page,//页数
              pageSize : this.page_count,//一页多少
              }
              }).then(res=>{
              if(res.data.code=="0"){
                        this.position = res.data.data.records 
                        this.fenlei=m
              }else{
                this.$message({
                            type:"error",
                            message: "数据异常"
                          })
        }
      })
    },
    po(key){
      router.push('/indexposition/'+key);
    }
  },
}
</script>