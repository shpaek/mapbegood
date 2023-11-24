<template lang="">
    <div class="group" @click="groupClickHandler"> <!-- v-for로 이미지 여러개 보이게 하기 -->
        <h2>내 그룹</h2>
        <img :src="image" alt="그룹이미지" class="img-size">
        <br>
        <span class="group-info">{{name}}({{listCnt}})</span>
        <br>
        <span class="group-mem-info">{{leader}}({{memCnt}})</span>
    </div>
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
            //주소이동시키기
            // loaction.href='/' 
        }
    }
}
</script>
<style scoped>
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
</style>