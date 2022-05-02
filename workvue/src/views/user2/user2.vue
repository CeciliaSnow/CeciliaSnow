<template>
<div class="b">
    <el-container
    class="layout-container-demo"
    style="width:70%;height: 100vh;margin:0 0;margin:auto;background-color:white; border: 1px solid #eee"
  >
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-scrollbar>
            <el-menu
              :default-active="this.$route.path"
             router>
                <!-- <el-menu-item index="person2"><el-icon><message /></el-icon>
                我的信息</el-menu-item>
                <el-menu-item index="/position1"><el-icon><message /></el-icon>正在审核</el-menu-item>
                <el-menu-item index="/position2"><el-icon><message /></el-icon>我的发布</el-menu-item>
                <el-menu-item index="/beaccepted"><el-icon><message /></el-icon>已被接取</el-menu-item>
                <el-menu-item index="/employ"><el-icon><message /></el-icon>雇佣管理</el-menu-item>
                <el-menu-item index="/complete2"><el-icon><message /></el-icon>已完成</el-menu-item>
                <el-menu-item index="/giveups0"><el-icon><message /></el-icon>终止审核</el-menu-item>
                <el-menu-item index="/giveups"><el-icon><message /></el-icon>已终止</el-menu-item> -->
                 <el-menu-item v-for="(l,key) in list" :key="key" :index="l.path"><el-icon><message /></el-icon>{{l.name}}</el-menu-item>
            </el-menu>
        </el-scrollbar>
        </el-aside>
        <el-main>
            <router-view/>
        </el-main>
    </el-container>
</div>
</template>

<style scoped>
.b{
    background-color:#f6f8f9;
}
.el-main{
    height: 120vh;
    background-color: white;
    overflow: hidden;
}
</style>
<script scoped>

export default {
  name: 'user2',
  data(){
      return{
          list:[
              {path:"/person2",name:"基本信息"},
              {path:"/position1",name:"正在审核"},
              {path:"/position2",name:"我的发布"},
              {path:"/beaccepted",name:"已被接取"},
              {path:"/employ",name:"雇佣管理"},
              {path:"/complete2",name:"已完成"},
              {path:"/giveups0",name:"终止审核"},
              {path:"/giveups",name:"已终止"},
          ]
      }
  }
  
}
</script>