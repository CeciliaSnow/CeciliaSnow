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
            class="el-menu-vertical-demo"
             router>
                <!-- <el-menu-item index="person"><el-icon><message /></el-icon>
                基本信息</el-menu-item>
                <el-menu-item index="accepted"><el-icon><message /></el-icon>已接取</el-menu-item>
                 <el-menu-item index="work"><el-icon><message /></el-icon>工作管理</el-menu-item>
                <el-menu-item index="complete"><el-icon><message /></el-icon>已完成</el-menu-item>
                 <el-menu-item index="giveup0"><el-icon><message /></el-icon>终止审核</el-menu-item>
                <el-menu-item index="giveup"><el-icon><message /></el-icon>已终止</el-menu-item> -->
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
  name: 'user',
  data(){
      return{
          list:[
              {path:"/person",name:"基本信息"},
              {path:"/accepted",name:"已接取"},
              {path:"/work",name:"工作管理"},
              {path:"/complete",name:"已完成"},
              {path:"/giveup0",name:"终止审核"},
              {path:"/giveup",name:"已终止"},
          ]
      }
  } 
}
</script>