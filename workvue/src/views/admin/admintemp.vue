<template>
<div style="height:33px;">
<el-input
      style="width:15%;float:left;"
      v-model="search"
      class="w-50 m-2"
      size="default"
      placeholder="请输入标题"
      :prefix-icon="Search"
    ></el-input>
    <el-button style="float:left;" type="primary" :icon="Search" @click="load">搜索</el-button>
</div>
  <el-table
      :data="tableData"
      style="width:100%;"
      :row-class-name="tableRowClassName"
  >
    <el-table-column prop="title" label="标题" width="250"></el-table-column>
    <el-table-column prop="name" label="发布者" width="180"></el-table-column>
    <el-table-column prop="category" label="类别" width="180"></el-table-column>
    <el-table-column prop="money" label="佣金" width="180"></el-table-column>
    <el-table-column prop="time" label="期限" width="180"></el-table-column>
    <el-table-column fixed="right" label="操作" width="260">
      <template #default="scope">
        
        <el-button @click="infor(scope.row)" type="text" size="small">详情</el-button>
        <el-button type="text" size="small" @click="yes(scope.row)">通过</el-button>
        <el-button type="text" size="small" @click="no(scope.row)">拒绝</el-button>
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

  <!-- 详情弹窗 -->
  <el-dialog v-model="dialogVisible" title="审核详情" open-delay="20">
   <div style="margin-left:50px">
  <h1>标题:&nbsp;&nbsp;&nbsp;{{position2.title}}</h1>
  <h3>发布人:&nbsp;&nbsp;&nbsp;{{position2.name}}</h3>
  <h4>类别:&nbsp;&nbsp;&nbsp;{{position2.category}}</h4>
  <h4>佣金:&nbsp;&nbsp;&nbsp;{{position2.money}}元</h4>
  <h4>期限:&nbsp;&nbsp;&nbsp;{{position2.time}}天</h4>
  <h4>负责人:&nbsp;&nbsp;&nbsp;{{position2.people}}</h4>
  <h4>联系方式:&nbsp;&nbsp;&nbsp;{{position2.phone}}</h4>
  <h4>联系邮箱:&nbsp;&nbsp;&nbsp;{{position2.email}}</h4>
  <h4>详情</h4>
      <div v-html="position2.content"></div>
   </div>
  <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="yes2(position2);dialogVisible = false;">通过</el-button>
        <el-button @click="no2(position2);dialogVisible = false;">拒绝</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
 
</style>
<script scoped>
import E from 'wangeditor'
import axioss from '../../utils/axios';
let editor
export default {
  name: 'admintemp',
  data() {
      return {
          dialogVisible:false,
          id:'',
          position2:{},
          
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
        axioss.get("/temp/queryalltemps",{
        params:{
        pageNum : this.currentPage,
        pageSize : this.pageSize,
        search : this.search}
        }).then(res=>{
                this.tableData = res.data.data.records,
                this.total=res.data.data.total
          })
      },
      handleEdit(row){
        this.position2=JSON.parse(JSON.stringify(row))  
      },
      infor(row){
        this.handleEdit(row);
        this.dialogVisible=true;        
      },
      yes(row){
        this.handleEdit(row)
        axioss.post('/temp/yes',this.position2).then(res => {
          if(res.data.code === "0"){
                this.load()
                this.$message({
                type: "success",
                message:"成功"
              })
            }else{
              this.$message({
                type: "error",
                message:"通过失败"
              })
            }
        })
      },
      yes2(row){
        axioss.post('/temp/yes',row).then(res => {
          if(res.data.code === "0"){
                this.load()
                this.$message({
                type: "success",
                message:"成功"
              })
            }else{
              this.$message({
                type: "error",
                message:"通过失败"
              })
            }
        })
      },
      no(row){
        this.handleEdit(row)
        axioss.get('/temp/no/'+this.position2.id).then(res=>{
            if(res.data.code === "0"){
                this.load()
                this.$message({
                type: "success",
                message:"拒绝成功"
              })
            }else{
              this.$message({
                type: "error",
                message:"拒绝失败"
              })
            }
        })
      },
      no2(row){
      
        axioss.get('/temp/no/'+row.id).then(res=>{
            if(res.data.code === "0"){
                this.load()
                this.$message({
                type: "success",
                message:"拒绝成功"
              })
            }else{
              this.$message({
                type: "error",
                message:"拒绝失败"
              })
            }
        })
      }
  },
}
</script>