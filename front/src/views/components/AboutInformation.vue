<script setup>
// example components
import DefaultInfoCard from "@/examples/cards/infoCards/DefaultInfoCard.vue";
import CenteredBlogCard from "@/examples/cards/blogCards/CenteredBlogCard.vue";
</script>
<template>
    <section class="py-2">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-6">
                    <div class="row justify-content-start">
                        <DefaultInfoCard color="info" icon="public" title="Connects Every Traveler"
                            description="여행에 필요한 모든 정보를 하나의 모바일 서비스로 이용하세요." />
                        <DefaultInfoCard color="info" icon="payments" title="Payments do not need"
                            description="무료로 AI를 활용한 만화 여행기 제작 등 다양한 서비스를 누려보세요.  " />
                    </div>
                    <div class="row justify-content-start mt-4">
                        <DefaultInfoCard color="info" icon="apps" title="Easy to create plan"
                            description="여행지를 검색하고 한번의 클릭만으로 편하게 계획에 등록하세요." />
                        <DefaultInfoCard color="info" icon="3p" title="Easy to make Diary"
                            description="여행을 마치시고 여행기를 작성한 후 한번의 클릭으로 나의 여행기를 만화로!" />
                    </div>
                </div>
                <div class="col-lg-4 ms-auto mt-lg-0 mt-6 py-1">
                    <CenteredBlogCard
                        image="https://www.techm.kr/news/photo/202405/124313_159959_3931.jpg"
                        description="대한민국 통신과 ICT 산업을 이끌어온 기업, KT는 언제나 고객과 가장 가까운 곳에서 함께 해왔습니다." />
                </div>
            </div>
        </div>
    </section>
</template>
