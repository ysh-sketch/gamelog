<script setup>
import { ref } from 'vue'
import { RouterLink, RouterView } from 'vue-router'
const token = ref(localStorage.getItem('token'))
const username = ref(localStorage.getItem('username'))

function logout() {
  localStorage.removeItem('token')
  localStorage.removeItem('username')
  token.value = null
  username.value = null
}
</script>

<template>
  <div class="min-h-screen bg-gray-950 text-gray-100">
    <header class="bg-gray-900 px-6 py-3 flex items-center justify-between">
      <RouterLink to="/" class="text-xl font-bold">gamelog</RouterLink>
      <nav v-if="!token" class="space-x-4">
        <RouterLink to="/login" class="text-gray-300 hover:text-white">登录</RouterLink>
        <RouterLink to="/register" class="text-gray-300 hover:text-white">注册</RouterLink>
      </nav>
      <div v-else class="space-x-4 flex items-center">
        <span class="text-gray-300">{{ username }}</span>
        <button @click="logout" class="text-gray-400 hover:text-white">退出</button>
      </div>
    </header>
    <RouterView />
  </div>
</template>
s