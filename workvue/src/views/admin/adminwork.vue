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
    <el-button style="float:left;" type="primary" :icon="Search" @click="load()">搜索</el-button>
</div>
  <el-table
      :data="tableData"
      style="width:100%;"
      :row-class-name="tableRowClassName"
  >
    <el-table-column prop="title" label="标题" width="250"></el-table-column>
    <el-table-column prop="name1" label="发布者" width="140"></el-table-column>
    <el-table-column prop="name2" label="接受者" width="140"></el-table-column>
    <el-table-column prop="category" label="类别" width="100"></el-table-column>
    <el-table-column prop="money" label="佣金" width="100"></el-table-column>
    <el-table-column prop="time" label="期限" width="100"></el-table-column>
    <el-table-column fixed="right" label="操作" width="160">
      <template #default="scope">
        <el-button type="text" size="small" @click="infor(scope.row)">详情</el-button>
        <el-button type="text" size="small" @click="del(scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <div>
 
  </div>
  <!-- 详情弹窗 -->
  <el-dialog v-model="dialogVisible" title="审核详情" open-delay="20">
   <div style="margin-left:50px">
  <h1>标题:&nbsp;&nbsp;&nbsp;{{position2.title}}</h1><br>
  <h3>发布人:&nbsp;&nbsp;&nbsp;{{position2.name1}}</h3><br>
  <h3>接受人:&nbsp;&nbsp;&nbsp;{{position2.name2}}</h3><br>
  <h4>类别:&nbsp;&nbsp;&nbsp;{{position2.category}}</h4><br>
  <h4>佣金:&nbsp;&nbsp;&nbsp;{{position2.money}}元</h4><br>
  <h4>期限:&nbsp;&nbsp;&nbsp;{{position2.time}}天</h4><br>
  <h4>详情</h4><br>
      <div v-html="position2.content"></div>
   </div>
  <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script scoped>
import axioss from '../../utils/axios'
export default {
  name: "adminwork",
  data(){
    return {
      dialogVisible: false,
      search:'',
      position2:'',
      tableData: [],
  }
},
  created(){
    this.load()
  },
  computed:{
    formatDate(){
        return formatDate;
      }
  },
  methods: {
    load(){
      axioss.get("/work/query",{params:{search:this.search}}).then(res=>{
        this.tableData = res.data.data
      })
    },
    handleEdit(row) {
      // 隔离
      this.position2 = JSON.parse(JSON.stringify(row))  
    },
    infor(row){
      this.handleEdit(row),
      this.dialogVisible = true
    },
    // 删除
    del(row){
        this.handleEdit(row)
        axioss.get("/work/admindel/"+this.position2.id).then(res =>{
           if(res.data.code === "0"){
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
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex === 1) {
        return 'warning-row'
      } else if (rowIndex === 3) {
        return 'success-row'
      }
      return ''
    },
    
  },
}
</script>

<style scoped>

</style>