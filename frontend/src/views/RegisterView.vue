<script setup>
import { ref } from 'vue'

const username = ref('')
const password = ref('')

function handleRegister() {
    fetch('http://localhost:8080/users', {
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
                console.log('注册成功，请去登录页面')
            }
            else {
                console.log(body.message)
            }
        })
}
</script>
<template>
    <div>
        <h1>注册</h1>
        <input v-model="username" placeholder="用户名" />
        <input v-model="password" type="password" placeholder="密码" />
        <button @click="handleRegister" class="bg-green-500 text-white px-4 py-2 rounded">注册</button>
    </div>
</template>