<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const name = ref('')
const platform = ref('')
const state = ref('在玩')
const rating = ref('')
const reflection = ref('')

function handleSubmit() {
    fetch('http://localhost:8080/games', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
        body: JSON.stringify({
            name: name.value,
            platform: platform.value,
            state: state.value,
            rating: rating.value,
            reflection: reflection.value
        })
    })
    .then(res => res.json())
        .then(body => {
            if (body.code == 0) {
                router.push('/')
            } else {
                console.log(body.message)
            }
        })
}
</script>

<template>
    <main class="max-w-md mx-auto px-4 py-8">
        <h1 class="text-2xl font-bold mb-6">添加游戏</h1>
        <div class="space-y-4">
            <div>
                <label class="block text-sm text-gray-400 mb-1">游戏名</label>
                <input v-model="name" placeholder="如：塞尔达传说：旷野之息"
                    class="w-full bg-gray-800 text-gray-100 px-3 py-2 rounded-lg" />
            </div>
            <div>
                <label class="block text-sm text-gray-400 mb-1">平台</label>
                <input v-model="platform" placeholder="PC / Switch"
                    class="w-full bg-gray-800 text-gray-100 px-3 py-2 rounded-lg" />
            </div>
            <div>
                <label class="block text-sm text-gray-400 mb-1">状态</label>
                <select v-model="state"
                    class="w-full bg-gray-800 text-gray-100 px-3 py-2 rounded-lg">
                    <option>在玩</option>
                    <option>通关</option>
                    <option>弃坑</option>
                </select>
            </div>
            <div>
                <label class="block text-sm text-gray-400 mb-1">评分</label>
                <input v-model="rating" placeholder="0-10"
                    class="w-full bg-gray-800 text-gray-100 px-3 py-2 rounded-lg" />
            </div>
            <div>
                <label class="block text-sm text-gray-400 mb-1">感想</label>
                <textarea v-model="reflection" rows="4" placeholder="写点什么..."
                    class="w-full bg-gray-800 text-gray-100 px-3 py-2 rounded-lg"></textarea>
            </div>
            <button @click="handleSubmit"
                class="px-4 py-2 bg-indigo-600 hover:bg-indigo-500 text-white rounded-lg">提交</button>
        </div>
    </main>
</template>
