<template>
<div class="common-layout" style="height:100vh">
    <el-container>
      <el-header>
        <Header />
      </el-header>
      <el-main>
        <router-view/>
      </el-main>
      
    </el-container>
  </div>
 
</template>

<script>
import Header from "../components/Header";
export default {
  name: "layout",
  components: {
    Header
  }
}
</script>

<style>
.el-container { height: 100%; } 
.el-main{ background-color: #f6f8f9;}
</style>