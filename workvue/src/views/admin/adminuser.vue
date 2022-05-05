<template>
<div style="height:40px;float:right;padding-right:200px">
  <el-button style="float:left;" type="primary" round @click="add">增加用户</el-button><br>
</div>
<div style="height:33px;">
<el-input
      style="width:15%;float:left;"
      v-model="search"
      class="w-50 m-2"
      size="default"
      placeholder="请输入昵称"
      :prefix-icon="Search"
    ></el-input>
    <el-button style="float:left;" type="primary" :icon="Search" @click="load">搜索</el-button>
</div>
  <el-table
      v-loading="loading"
      :data="tableData"
      style="width:100%;"
      :row-class-name="tableRowClassName"
  >
    <el-table-column prop="id" label="id" width="100"> </el-table-column>
    <el-table-column label="头像" width="180">
    <template #default="scope">   
            <img v-if="scope.row.pic" :src="scope.row.pic"  min-width="70" height="70" />
            <p v-else>无头像</p>
    </template>
    </el-table-column>
    <el-table-column prop="name" label="昵称" width="180"> </el-table-column>
    <el-table-column prop="username" label="用户名" width="180"> </el-table-column>
    <el-table-column prop="sex" label="性别" width="100"> </el-table-column>
    <el-table-column prop="email" label="邮箱" width="260"> </el-table-column>
    <el-table-column fixed="right" label="操作" width="180">
      <template #default="scope">
        <el-button @click="handleEdit(scope.row)" type="text" size="small">编辑</el-button>
      
        <el-button type="text" size="small" @click="del(scope.row.id)">删除</el-button>
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
  <!-- 详情 -->
   <el-dialog v-model="dialogFormVisible2" title="用户信息">
      <div style="margin-left:50px">
        <h1>昵称:&nbsp;&nbsp;&nbsp;{{tableData.name}}</h1>
        <h3>发布人:&nbsp;&nbsp;&nbsp;{{tableData.username}}</h3>
        <h4>类别:&nbsp;&nbsp;&nbsp;{{tableData.password}}</h4>
        <h4>佣金:&nbsp;&nbsp;&nbsp;{{tableData.money}}元</h4>
        <h4>期限:&nbsp;&nbsp;&nbsp;{{tableData.time}}天</h4>
   </div>
     <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible2 = false">取消</el-button>
        <el-button type="primary" >确认</el-button>
      </span>
    </template>
  </el-dialog>
  <!-- 编辑表单 -->
  <el-dialog v-model="dialogFormVisible" title="用户信息">
    <el-form :model="form">
      <el-form-item label="昵称">
      <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="用户名">
      <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="密码">
      <el-input type="password" v-model="form.password"></el-input>
      </el-form-item>
      <el-form-item label="权限" prop="role">
        <el-radio-group v-model="form.role">
          <el-radio label="1">工作者</el-radio>
          <el-radio label="2">招聘者</el-radio>
          <el-radio label="3">管理员</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-radio-group v-model="form.sex">
          <el-radio label="男"></el-radio>
          <el-radio label="女"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="邮箱">
      <el-input v-model="form.email"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false;save()">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script scoped>
import axioss from '../../utils/axios'
export default {
  name: "adminuser",
  data(){
    return {
      loading:true,
      dialogFormVisible: false,
      dialogFormVisible2: false,
      formLabelWidth :'120px',
      form:{},
      user:{},

      currentPage: 1,
      total:10,
      pageSize:10,
      search:'',
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
     dateFormatter (key) {
        let datetime = row.time;
        if(datetime){
          datetime = new Date(datetime);
          let y = datetime.getFullYear() + '-';
          let mon = datetime.getMonth()+1 + '-';
          let d = datetime.getDate();
          return y + mon + d;
        }
        return ''
      },
    load(){
      axioss.get("/user/adminusers",{
        params:{
        pageNum : this.currentPage,
        pageSize : this.pageSize,
        search : this.search}
        }).then(res=>{
        this.tableData = res.data.data.records,
        console.log(this.tableData)
        this.total=res.data.data.total
        this.loading=false
      })
    },
    add(){
      this.dialogFormVisible = true
    },
    handleEdit(row) {
      this.dialogFormVisible = true,
      // 隔离
      this.form = JSON.parse(JSON.stringify(row))
      
    },
    // 更改保存
    save(){
        if(this.form.id){
          axioss.put('/user/adminupdate',this.form).then(res=>{
            if(res.data.code === "0"){
              this.$message({
            type: "success",
            message:"更新成功"
            })
            this.load()
            }else{
              this.$message({
                type: "error",
                message:res.data.msg
              })
            }
          })
        }else{
          axioss.post("/user/register",this.form).then(res =>{
        if(res.data.code === "0"){
              this.$message({
            type: "success",
            message:"添加成功"
            })
            this.dialogFormVisible = false
            this.load()
            }else{
              this.$message({
                type: "error",
                message:"添加失败"
              })
            }
      })
        }
    },
    // 逻辑删除
    del(id){
        axioss.delete("/user/admindel/"+id).then(res =>{
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
    
    handleSizeChange(val) {
      
    },
    handleCurrentChange(val) {
      
    },
  },
}
</script>

<style scoped>
.el-input{
  width: 300px;
}
</style>