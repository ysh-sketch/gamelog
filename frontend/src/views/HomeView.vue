<script setup>
import { ref } from 'vue'

const games = ref([])

fetch('http://localhost:8080/games')
  .then(res => res.json())
  .then(body => { games.value = body.data })
</script>

<template>
  <main class="max-w-4xl mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold text-gray-100 mb-6">游戏库</h1>
    <ul class="space-y-2">
      <li v-for="g in games" :key="g.id"
        class="bg-gray-800 text-gray-100 p-4 rounded-lg hover:bg-gray-700 cursor-pointer">
        {{ g.name }} · {{ g.rating }} 分
      </li>
    </ul>
  </main>
</template>