<template lang="">
    <form class="groupcreate" @submit.prevent="groupcreateFormSubmitHandler">
        <!-- 그룹 생성입니다 -->
        <h2>새 그룹 생성</h2>
        <div class="img">
            <label for="i">그룹 이미지</label>
            <br>
            <img class="img" :src="image" alt="그룹이미지">
            <br>
            <input type="file" name="img" id="i" @change="imageChangeHandler"/>
        </div>

        <label for="n">그룹명 </label>
        <input type="text" name="name" id="n" maxlength="20" size="20"
               placeholder="그룹명은 20자 이내로 입력"/>
        <br>

        <label for="m">멤버추가 </label>
        <br>
        <label for="ms">추가된 멤버 </label>
        <br>
        <!-- 멤버를 검색해서 검색결과가 나오고 버튼으로 추가한다고 했는데 
            피그마에서는 멤버명 입력하고 검색하면 있을 때만 추가해서 todolist쌓이듯 보이게 할거라 써져있었음
            상의 결과 사용자검색 기능 가져올 때 남은시간 고려해서 디테일 추가하기로 함 뭘로하든 괜찮다고 하셨음 -->

        <button type="submit" id="b1">그룹생성</button>
        <button type="button" id="b2" @click="b2ClickHandler">생성취소</button>
    </form>
</template>
<script>
import axios from 'axios';
export default {
    name: "GroupCreate",
    data(){
        return{    
            image: '../../../public/images/defaultGroupProfile.jpg',
            name: '',
            // memberlist: [],  
            // member: '',
        }
    },
    methods:{
        groupcreateFormSubmitHandler(e){
            //axios로 백 url요청
        },
        imageChangeHandler(e){
            const url = URL.createObjectURL(e.target.files[0]) //<input type="file">선택된 파일자원
            this.image = url //$('form.signup img.profile').attr('src', url)
        },
        b2ClickHandler(){
            alert("그룹생성을 취소합니다")
            location.href="/group"
        }
    }
}
</script>
<style scoped>
    
</style>