<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const comments = ref([])
const game = ref(null)
const content = ref('')

function postComment() {
    fetch('http://localhost:8080/comments', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
        body: JSON.stringify({
            gameId: Number(route.params.id),
            content: content.value,
        }),
    })
        .then(res => res.json())
        .then(body => {
            if (body.code == 0) {
                console.log('评论成功')
                content.value = ""
                loadComments()
            } else {
                console.log('失败：', body.message)
            }
        })
}

function loadComments() {
    fetch('http://localhost:8080/comments/' + route.params.id)
        .then(response => response.json())
        .then(body => { comments.value = body.data })
}

loadComments()

fetch('http://localhost:8080/games/' + route.params.id)
    .then(response => response.json())
    .then(body => { game.value = body.data })

</script> 

<template>
    <main class="max-w-4xl mx-auto px-4 py-8" v-if="game">
        <img v-if="game.screenshot" :src="'http://localhost:8080/uploads/' + game.screenshot"
            class="h-48 w-full object-cover" />
        <div v-else class="h-48 bg-gradient-to-br from-indigo-600 to-purple-700 rounded-lg"></div>
        <h1 class="text-3xl font-bold mt-4">{{ game.name }}</h1>
        <p class="text-gray-400 mt-2">{{ game.platform }} · {{ game.state }} · {{ game.rating }} 分</p>
        <p v-if="game.reflection" class="mt-4 text-gray-300">{{ game.reflection }}</p>


        <!--评论区-->
        <h2 class="text-xl font-bold mt-8 mb-4">评论</h2>

        <div class="mt-4 flex gap-2">
            <input v-model="content" placeholer="评论些什么吧"
                class="flex-1 bg-gray-800 text-gray-100 px-3 py-2 rounded-lg" />
            <button @click="postComment"
                class="px-4 py-2 text-sm bg-indigo-600 hover:bg-indigo-500 text-white rounded-lg">发表</button>
        </div>

        <p v-if="comments.length === 0" class="text-gray-400">还没有评论</p>

        <div v-else>
            <div v-for="c in comments" :key="c.id" class="bg-gray-800 p-3 rounded-lg mb-3">
                <div class="font-bold">{{ c.username }}</div>
                <div>{{ c.content }}</div>
                <div class="text-xs text-gray-400 mt-1">{{ c.createdAt }}</div>
            </div>
        </div>
    </main>
</template>