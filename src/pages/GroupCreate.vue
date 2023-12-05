<template lang="">
    <form class="groupcreate" @submit.prevent="groupcreateFormSubmitHandler">
        <!-- 그룹 생성입니다 -->
        <h2>새 그룹 생성</h2>
        <div class="fill">
            <div class="img">
                <label for="i">그룹 이미지 선택</label>
                <br>
                <img class="img" :src="image" alt="그룹이미지">
                <br>
                <input type="file" name="img" id="i" @change="imageChangeHandler"/>
            </div>
            <hr>
            <label for="n">그룹명</label>&nbsp;
            <input type="text" name="name" id="n" v-model="name" maxlength="20"
                   placeholder="20자 이내로 입력하세요" required/>
            <br>    
            <!-- <label for="m">멤버 초대</label>&nbsp;
            <button>검색</button>
            <br>
            <label for="ms">초대할 멤버</label>&nbsp;
            <span>초대할 멤버 목록</span> <br> -->
            <!-- 원래는 미리 초대할 멤버를 정하기로 했는데 그러면 groupid생성 전이라 waiting에 추가하기 복잡해져서
                 그룹을 만들고 수정할 때 바꾸도록 하는 게 나을 듯 -->
        </div>
        <div class="button-container">
            <button type="submit" class="create" id="b1">그룹 생성</button>&nbsp;&nbsp;&nbsp;
            <button type="button" class="cancel" id="b2" @click="b2ClickHandler">생성 취소</button>
        </div>
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
        groupcreateFormSubmitHandler(e){//그룹 생성 버튼 클릭 시
            //axios로 백 url요청
            const url = `${this.backURL}/group`
            const fd = new FormData(e.target)
            const data = fd

            axios.post(url,data, {contentType: false, //파일첨부용 프로퍼티
                                  processData: false})
                 .then(response => {
                    alert("결과:"+response.data.msg)
                 })
                 .catch(error => {
                    alert(error.message)
                 })
        },
        imageChangeHandler(e){
            const url = URL.createObjectURL(e.target.files[0]) //<input type="file">선택된 파일자원
            this.image = url //$('form.signup img.profile').attr('src', url)
        },
        b2ClickHandler(){ //생성 취소 버튼 클릭 시
            alert("그룹 생성을 취소합니다")
            location.href="/group"
        },
       
       
    }
}
</script>
<style scoped>
div.fill{
    margin-top:100px;
    margin-bottom:100px;
    margin-left:100px;
    border: 1px solid lightgrey;
    padding:25px;
    width: 500px;
}
div.fill>label{
    font-size:15px;
}
div.fill>div.img>label{
    font-size:15px;
}
div.fill>div.img>img.img-size{
    max-width: 120px; /* 화면에서 보여줄 사이즈로 조정 */
    max-height: 120px;
    min-height: 120px;
    min-width: 120px;
}
div.button-container{
    /* display: flex;
    justify-content: center; */
    margin-left: 235px;
}
</style>