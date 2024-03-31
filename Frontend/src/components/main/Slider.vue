<template>
  <v-container class="d-flex justify-center">
    <div class="carousel-container">
      <div class="welcome mb-5 ml-10">
        <div class=""> 
          <span class="point-font text-3xl point-color">{{ useAuthStore().userName }}</span>
          <span class="text-2xl"> 님은</span>
        </div>
        <span class="text-2xl mt-3"> 오늘도 열공중! <span class="tossface text-3xl">🙋‍♀️</span></span>
      </div>
      <Carousel
        :items-to-show="2"
        :wrapAround="true"
        :transition="500"
        :autoplay="3000"
        :buttonVisibility="true"
      >
        <Slide v-for="feat in MainFeat" :key="feat.id">
          <div class="d-flex flex-column justify-center carousel__item">
            <!-- <img @click="feat.function" :src="feat.imgUrl" :alt="feat.id" class="carousel__image" /> -->
            <v-card class="w-full h-96 rounded-lg" variant="text" color="#000000" :image="feat.imgUrl" @click="feat.function">
              <div clas="d-flex flex-column bg-gray-300">
                <div class="image-filter" style="width: 750px; height: 500px">
                  <div class="card-title text-gray-50 text-end">{{ feat.content }}</div>
                  <div class="card-detail text-gray-200 text-end">{{ feat.detail }}</div>
                </div>
              </div>
            </v-card>
          </div>
        </Slide>
        <template #addons>
          <Navigation />
        </template>
      </Carousel>
    </div>
  </v-container>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { useMainPageStore } from '@/stores/mainPage'
import { Carousel, Slide, Navigation } from 'vue3-carousel'
import 'vue3-carousel/dist/carousel.css'

import noteBasicImage from '@/assets/image/home/NoteBasic.png'
import emptynote from '@/assets/image/home/WriteNote.png'

// 1. 개인스터디 이동
// 2. 최근작성한노트
// 3. 노트 작성

const mainPageStore = useMainPageStore()
const myStudyImg = computed(() => mainPageStore.myStudyImg)

const MainFeat = ref([
  {
    id: 1,
    function: () => mainPageStore.GoMyStudyRoom(mainPageStore.myStudy),
    imgUrl: myStudyImg,
    content: '개인 스터디',
    detail: '오늘은 무슨 주제에 대해 이야기 하나요?',
  },
  {
    id: 2,
    function: () => mainPageStore.GoRecentEditNote(mainPageStore.recentEditNote),
    imgUrl: noteBasicImage,
    content: '최근 노트',
    detail: '이 주제에 대해 공부했어요.',
  },
  { id: 3, 
    function: () => mainPageStore.GoCreateNote(), 
    imgUrl: emptynote, 
    content: '노트작성',
    detail: '공부한 내용을 정리하세요.', }
])
</script>

<style scoped>
.welcome {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: start;
  align-self: start;
}
.carousel {
  width: 1300px;
}
.container {
  display: flex;
  flex-direction: column;
  justify-content: center; /* 중앙 정렬을 위한 추가 */
  align-items: center;
}
.carousel-container {
  width: 1300px;
  display: flex;
  flex-direction: column;
  justify-content: center; /* 중앙 정렬을 위한 추가 */
  align-items: center;
}
.carousel__item {
  width: 1000px;
  height: 500px;
  object-fit: cover; /* 이미지 비율 유지 및 잘림 방지 */
}

.carousel__viewport {
  perspective: 1100px;
}

.carousel__track {
  transform-style: preserve-3d;
}

.carousel__slide--sliding {
  transition: 0.5s;
}

.carousel__slide {
  opacity: 1;
  transform: rotateY(0);
}

.carousel__slide--active ~ .carousel__slide {
  transform: rotateY(0) scale(1);
}

.carousel__slide--prev {
  opacity: 0.3;
  transform: scale(1);
}

.carousel__slide--next {
  opacity: 0.3;
  transform: rotateY(0);
}

.carousel__slide--active {
  opacity: 1;
  transform: rotateY(0) scale(1.2);
  z-index: 100;
}

.carousel__image {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 이미지 비율 유지 및 잘림 방지 */
}

.image-filter {
  background-color: #00000099;
}

.card-title {
  font-size: 65px;
  padding-right: 40px;
  padding-top: 220px;
}
.card-detail {
  font-size: 20px;
  padding-right: 40px;
  padding-top: 0px;
}

</style>
