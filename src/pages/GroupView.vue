<template lang="">
    <h2>내 그룹</h2>
    <div class="group" @click="groupClickHandler"> <!-- v-for로 이미지 여러개 보이게 하기 -->
        <img :src="image" alt="그룹이미지" class="img-size">
        <br>
        <span class="group-info">{{name}}({{listCnt}})</span>
        <br>
        <span class="group-mem-info">{{leader}}({{memCnt}})</span>
    </div>
    <br>
    <span class="add-group" @click="addgroupClickHandler">
        <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-plus-circle-fill" viewBox="0 0 16 16">
         <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM8.5 4.5a.5.5 0 0 0-1 0v3h-3a.5.5 0 0 0 0 1h3v3a.5.5 0 0 0 1 0v-3h3a.5.5 0 0 0 0-1h-3v-3z"/>
        </svg>
    </span>
</template>
<script>
export default {
    name: "GroupView",
    data(){
        return{
            name: '그룹명',
            listCnt: 0,
            leader: '그룹장',
            memCnt: 0,
            image: '../../../public/images/defaultGroupProfile.jpg'
        }
    },
    methods:{
        groupClickHandler(){
            //그룹의 테마그룹 List보여주는 탭으로 주소이동시키기
            // loaction.href='/' 
        },
        addgroupClickHandler(){ //그룹추가 페이지로 이동
            location.href='/groupcreate'
        }

    }
}
</script>
<style scoped>
div.group{
    margin: 50px;
}
div.group>span.group-info{
    font-size: 15px;
    padding-left: 10px;
}
div.group>span.group-mem-info{
    font-size: 10px;
    padding-left: 10px;
}
div.group>img.img-size{
    max-width: 120px; /* 화면에서 보여줄 사이즈로 조정 */
    max-height: 120px;
}
span.add-group{
    display: flex;
    margin-left: 30px;
}
</style>