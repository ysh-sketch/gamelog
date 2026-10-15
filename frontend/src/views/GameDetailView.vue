<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const comments = ref([])
const game = ref(null)

fetch('http://localhost:8080/games/' + route.params.id)
    .then(response => response.json())
    .then(body => { game.value = body.data })

fetch('http://localhost:8080/comments/' + route.params.id)
    .then(response => response.json())
    .then(body => { comments.value = body.data })
</script>

<template>
    <main class="max-w-4xl mx-auto px-4 py-8" v-if="game">
        <div class="h-48 bg-gradient-to-br from-indigo-600 to-purple-700 rounded-lg"></div>
        <h1 class="text-3xl font-bold mt-4">{{ game.name }}</h1>
        <p class="text-gray-400 mt-2">{{ game.platform }} · {{ game.state }} · {{ game.rating }} 分</p>
        <p v-if="game.reflection" class="mt-4 text-gray-300">{{ game.reflection }}</p>


        <!--评论区-->
        <h2 class="text-xl font-bold mt-8 mb-4">评论</h2>

        <p v-if="comment.length === 0" class="text-gray-400">还没有评论</p>

        <div v-else>
            <div v-for="c in comments" :key="c.id" class="bg-gray-800 p-3 rounded-lg mb-3">
                <div class="font-bold">{{ c.username }}</div>
                <div>{{ c.content }}</div>
                <div class="text-xs text-gray-400 mt-1">{{ c.createdAt }}</div>
            </div>
        </div>
    </main>
</template>


cd ~/aigc-run && venv/bin/python - <<'EOF'
import sys; sys.path.insert(0, "code")
from create_input_files import encode_captions

wm = {"<pad>": 0, "<unk>": 1, "<start>": 2, "<end>": 3}
for n in (5, 50, 51, 52, 60):
    ret, seqlen = encode_captions(["w"] * n, wm, 52)
    ok = "✓" if len(ret) == 52 and ret.count(3) == 1 else "✗"
    print(f"n={n:3d} 总长{len(ret):3d} end个数{ret.count(3)} {ok}")
EOF