<template>
  <el-container class="voluntary-shell">
    <div class="layout-header">
      <Header/>
    </div>
    <el-main class="voluntary-main">
      <router-view/>
    </el-main>
    <el-footer class="voluntary-footer-wrap">
      <Footer/>
    </el-footer>
  </el-container>
</template>

<script>
import Header from "@/components/layout/Header";
import Footer from "@/components/layout/Footer";

export default {
  name: "Layout",
  components: {Footer, Header},
}
</script>

<style scoped>
.voluntary-main,
.voluntary-footer-wrap {
  padding: 0;
}
</style>
