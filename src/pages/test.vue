<template lang="">
    <div>
        <input type="text" v-model="email"/>
        <button @click="test">SSE받기</button>
    </div>
</template>
<script>
export default {
    name: 'test',
    data(){
        return {
            email : 'e1',
        }
    },
    methods: {
    test(){

        this.$sse.create({url:'http://localhost:8080/notifications/subs/'+this.email, withCredentials: true})
  .on('sse', (data) => console.log('Message:', data))
  .on('error', (err) => console.error('Failed to parse or lost connection:', err))
  .connect()
  .catch((err) => console.error('Failed make initial connection:', err));
    }
    },
    created(){
      
    }
}
</script>
<style lang="">
    
</style>