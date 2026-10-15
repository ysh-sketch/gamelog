<script setup>
import { ref } from 'vue'

const username = ref('')
const password = ref('')

function handleLogin() {
    fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            username: username.value,
            password: password.value,
        })
    })
        .then(res => res.json())
        .then(body => {
            if (body.code === 0) {
                localStorage.setItem('token', body.data)
                console.log('登录成功，token已入库')
                localStorage.setItem('token', body.data)
                localStorage.setItem('username', username.value)
            }
            else {
                console.log('登录失败')
            }
        })
}
</script>
<template>
    <div>
        <h1>登录</h1>
        <input v-model="username" placeholder="用户名" />
        <input v-model="password" type="password" placeholder="密码" />
        <button @click="handleLogin" class="bg-green-500 text-white px-4 py-2 rounded">登录</button>
    </div>
</template>