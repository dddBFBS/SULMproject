<script setup>

</script>

<template>
  <el-button type="danger" @click="testHandler">测试接口</el-button>

</template>

<script setup>
  import requestUtil from '@/util/request'
  const testHandler=async ()=>{
    let result=await requestUtil.get("test/user/list");
  }
</script>
<style scoped>

</style>