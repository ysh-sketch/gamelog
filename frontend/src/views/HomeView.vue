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
    <ul class="grid grid-cols-4 gap-4">
      <li v-for="g in games" :key="g.id"
        class="bg-gray-800 rounded-lg overflow-hidden hover:bg-gray-700 cursor-pointer">
        <div class="h-32 bg-gradient-to-br from-indigo-600 to-green-700"> </div>
        <div class="p-3">
          <div class="flex items-center justify-between">
            <div>
              <div class="font-bold">{{ g.name }}</div>
              <div class="text-sm text-gray-400">{{ g.platform }} · {{ g.rating }} 分</div>
            </div>
            <button class="mt-2 px-3 py-1.5 text-sm border border-indigo-500 text-indigo-400 hover:bg-indigo-600 hover:text-white rounded">
              查看
            </button>
          </div>
        </div>
      </li>
    </ul>
  </main>
</template>