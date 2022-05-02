<template>
<div style="height:33px;">
<el-input
      style="width:15%;float:left;"
      v-model="search"
      class="w-50 m-2"
      size="default"
      placeholder="Please Input"
      :prefix-icon="Search"
    ></el-input>
    <el-button style="float:left;" type="primary" :icon="Search" @click="load">搜索</el-button>
</div>
  <el-table :data="tableData"
  v-loading="loading"
      style="width:100%;"
      :row-class-name="tableRowClassName">
    <el-table-column prop="title" label="标题" width="250" />
    <el-table-column prop="name" label="发布者" width="180" />
    <el-table-column prop="category" label="类别" width="130" />
    <el-table-column prop="money" label="佣金" width="100" />
    <el-table-column prop="time" label="期限" width="100" />
    <el-table-column prop="num" label="接取人数" width="100" />
    <el-table-column fixed="right" label="操作" width="160">
      <template #default="scope">
      <el-button type="text" size="small" @click="info(scope.row)">详情</el-button>
      <el-button type="text" size="small" @click="no(scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <div>
   <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[5, 10, 15]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
  >
  </el-pagination>
  </div>
  <div style="flex:1;background-color:white;display:flex">

     
  </div>
  <!-- 详情弹窗 -->
  <el-dialog v-model="dialogVisible" title="发布详情">
   <div style="margin-left:50px;">
  <h1>标题:&nbsp;&nbsp;&nbsp;{{position2.title}}</h1>
  <h3>发布人:&nbsp;&nbsp;&nbsp;{{position2.name}}</h3>
  <h4>类别:&nbsp;&nbsp;&nbsp;{{position2.category}}</h4>
  <h4>佣金:&nbsp;&nbsp;&nbsp;{{position2.money}}元</h4>
  <h4>期限:&nbsp;&nbsp;&nbsp;{{position2.time}}天</h4>
  <h4>接取人数:&nbsp;&nbsp;&nbsp;{{position2.num}}</h4>
  <h4>负责人:&nbsp;&nbsp;&nbsp;{{position2.people}}</h4>
  <h4>联系方式:&nbsp;&nbsp;&nbsp;{{position2.phone}}</h4>
  <h4>联系邮箱:&nbsp;&nbsp;&nbsp;{{position2.email}}</h4>
  <div v-if="position2.num!=0" style="display:flex;" >
  <h4>接取人:&nbsp;&nbsp;&nbsp;</h4><div style="padding-top:16px;" v-for="i in users" :key="i.id"><el-tag>{{i.name}}</el-tag>&nbsp;&nbsp;&nbsp;</div>
  </div>
  <h4>详情</h4>
      <div v-html="position2.content"></div>
   </div>
  <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button @click="dialogVisible = false;">删除</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>

</style>
<script scopde>
import axioss from '../../utils/axios'
import accepted from '../user/accepted.vue'
export default {
  components: { accepted },
  name: 'admintemp',
  data() {
      return {
        loading:true,
          dialogVisible:false,
          id:'',
          position:[],
          position2:{},
          users:[],
          user:{},

          currentPage: 1,
          total:10,
          pageSize:10,
          search:'',
          tableData:[],
      }
  },
  created() {
      this.load()
  },
  methods: {
      load(){
        this.loading=true
        axioss.get("/position/queryallposition",{
        params:{
        pageNum : this.currentPage,
        pageSize : this.pageSize,
        search : this.search}
        }).then(res=>{
            this.tableData = res.data.data.records,
            this.total=res.data.data.total
            this.loading=false
          })
      },
      handleEdit(row){
        this.position2=JSON.parse(JSON.stringify(row))  
      },
      info(row){
        this.handleEdit(row);
        if(this.position2.num!=0){
          axioss.post("/position/beaccepted2/"+this.position2.id).then(res=>{
            this.users = res.data.data
          })
        }
        this.dialogVisible=true
        
      },
      userinfo(id){

        axioss.get("/user/sel/"+id).then(res=>{
          this.user = res.data.data
        })
      },
      no(row){
        this.handleEdit(row);
        axioss.get('/position/no/'+this.position2.id).then(res=>{
          console.log(this.position2.id)
            if(res.data.code === "0"){
                this.load()
                this.$message({
                type: "success",
                message:"已删除"
              })
            }else{
              this.$message({
                type: "error",
                message:"删除失败"
              })
            }
        })
      },
  },
}
</script>