<template lang="">
    <!-- 특정 그룹의 테마지도 목록을 전부 불러옴, 그룹이미지, 그룹명, 그룹장도 가져와야함 -->
    <form class="group-detail">
        <div class="group-info">
            <img :src="image" alt="그룹이미지" @doubleClick="imageDoubleClickHandler"/>
            <input type="file" id>
            <!-- imageClickHandler: 이미지 변경 -->

            <span class="group-name" @click="groupnameClickHandler">
                {{name}}({{listCnt}})
            </span>
            <!-- groupnameClickHandler: 그룹이름변경 -->

            <span class="group-member" @click="groupmemberClickHandler">
                {{leader}}({{memCnt}})
            </span>
            <!-- groupmemberClickHandler: 그룹멤버수정페이지로 이동 -->

        </div>
        <div class="new-group">
            <span>새 리스트 추가</span>
            <!-- 클릭이벤트 주기 -->
        </div>
        <div class="group-thememap-list">
            그룹의 테마지도목록입니다
            <div class="group-thememap">
                <span>{{listName}}</span>
                <span>{{listWriter}}</span>
            </div>
        </div>
    </form>
</template>
<script>
export default {
    name: "GroupThemeMapList",
    data(){
        return{
            name: '그룹명',
            listCnt: 0,
            leader: '그룹장',
            memCnt: 0,
            image: '../../../public/images/defaultGroupProfile.jpg',
            listName: '리스트명',
            listWriter: '리스트작성자'

        }
    },
    methods:{
        imageDoubleClickHandler(){

        }
    }
}
</script>
<style scoped>
    
</style>