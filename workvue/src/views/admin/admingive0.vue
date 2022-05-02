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
    <el-table-column prop="msg" label="理由" width="100"></el-table-column>
    <el-table-column fixed="right" label="操作" width="160">
      <template #default="scope">
        <el-button type="text" size="small" @click="infor(scope.row)">详情</el-button>
         <el-button type="text" size="small" @click="y(scope.row)">通过</el-button>
        <el-button type="text" size="small" @click="del(scope.row)">拒绝</el-button>
      </template>
    </el-table-column>
  </el-table>
  <div>
 
  </div>
  <!-- 详情弹窗 -->
  <el-dialog v-model="dialogVisible" title="详情" open-delay="20">
   <div style="float:right;padding-right:50px">
    <h3>审核理由:{{position2.msg}}</h3>
     </div>
      <h1>{{position2.title}}</h1>
      <h3>类别:&nbsp;{{position2.category}}</h3>
      <h3>佣金:&nbsp;{{position2.money}}元</h3>
      <h3>期限:&nbsp;{{position2.time}}天</h3>
      <h4>任务需求</h4>
       <div v-html="position2.content"></div>
      <hr>
    <h4>发布者:&nbsp;{{a.name}}</h4>
 <h4>执行人:&nbsp;{{b.name}}</h4>
  <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button @click="y2(position2);dialogVisible = false">通过</el-button>
        <el-button @click="del2(position2);dialogVisible = false">拒绝</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script scoped>
import axioss from '../../utils/axios'
export default {
  name: "admingive0",
  data(){
    return {
      dialogVisible: false,
      search:'',
      position2:'',
      tableData: [],
      a:{},
      b:{},
      
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
      axioss.get("/work/fangqis",{params:{search:this.search}}).then(res=>{
        this.tableData = res.data.data
        
      })
    },
    handleEdit(row) {
      // 隔离
      this.position2 = JSON.parse(JSON.stringify(row))  
    },
    infor(row){
      this.handleEdit(row),
      axioss.get('/user/sel/'+this.position2.userid).then(res=>{
          this.a = res.data.data
      })
      axioss.get('/user/sel/'+this.position2.user).then(res=>{
          this.b = res.data.data
      })
      this.dialogVisible = true
    },
    y(row){
         this.handleEdit(row)
          axioss.post("/work/fangqit",this.position2).then(res=>{
            if(res.data.code==="0"){
            this.load()
            this.$message({
                type:"success",
                message:"成功"
            })
            }
            })
    },
     y2(row){
          axioss.post("/work/fangqit",row).then(res=>{
            if(res.data.code==="0"){
            this.load()
            this.$message({
                type:"success",
                message:"成功"
            })
            }
            })
    },
    // 拒绝
    del(row){
        this.handleEdit(row)
        axioss.post("/work/fangqitz/"+this.position2.id).then(res =>{
           if(res.data.code === "0"){
              this.$message({
            type: "success",
            message:"成功"
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
    del2(row){
        axioss.post("/work/fangqitz/"+row.id).then(res =>{
           if(res.data.code === "0"){
              this.$message({
            type: "success",
            message:"成功"
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