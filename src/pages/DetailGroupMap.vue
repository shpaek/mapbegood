<!--DetailGroupMap.vue-->
<template>
   <div>
    <h1>{{ themeMap.name }}</h1>
    <p>{{ themeMap.memo }}</p>
  </div>


</template>
<script>
import axios from 'axios';

export default {
  name: 'DetailGroupMap',
   data(){
    return{
        thememap:{}, //테마지도 정보 저장할 객체
    };
   },
   created(){
    const groupId = this.$route.params.groupId;
    const groupThememapId = this.$route.params.groupThememapId;

    console.log('groupId:',groupId);
    console.log('groupThememapId',groupThememapId)
    
 // API를 통해 그룹 테마지도 정보 가져오기
 axios.get(`http://localhost:8080/ourmap/get/${groupId}/${groupThememapId}`)
      .then(response => {
        // API로부터 받은 데이터를 컴포넌트의 데이터에 저장
        this.themeMap = response.data;
      })
      .catch(error => {
        console.error('Error fetching theme map data:', error);
      });
  },
};
   

 
  </script>
  

 