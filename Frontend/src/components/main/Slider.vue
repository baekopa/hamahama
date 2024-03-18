<template>
  <v-container style="width: 1200px">
    <Carousel :itemsToShow="1.5" :wrapAround="true" :transition="500" :button-visibility="true">
      <Slide v-for="feat in MainFeat" :key="feat.id">
        <div class="carousel__item">
          <img :src="feat.imgUrl" :alt="feat.id" class="carousel__image" />
        </div>
      </Slide>
    </Carousel>
  </v-container>
</template>

<script setup>
import { ref } from 'vue'
import { Carousel, Slide, Pagination } from 'vue3-carousel'
import 'vue3-carousel/dist/carousel.css'

import Main_1 from '../../assets/image/main/karina.jpg'
import Main_2 from '../../assets/image/main/irean.jpg'
import Main_3 from '../../assets/image/main/ajeong.png'

const MainFeat = ref([
  { id: 1, imgUrl: Main_1 },
  { id: 2, imgUrl: Main_2 },
  { id: 3, imgUrl: Main_3 }
])
</script>

<style scoped>
.carousel__slide {
  padding: 5px;

  height: 300px;
}

.carousel__viewport {
  perspective: 100px;
}

.carousel__track {
  transform-style: preserve-3d;
}

.carousel__slide--sliding {
  transition: 0.5s;
}

.carousel__slide {
  opacity: 0.9;
  transform: rotateY(-20deg) scale(1);
}

.carousel__slide--active ~ .carousel__slide {
  transform: rotateY(20deg) scale(0.9);
}

.carousel__slide--prev {
  opacity: 1;
  transform: rotateY(-10deg) scale(0.9);
}

.carousel__slide--next {
  opacity: 1;
  transform: rotateY(10deg) scale(0.9);
}

.carousel__slide--active {
  opacity: 1;
  transform: rotateY(0) scale(1);
}

.carousel__image {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 이미지 비율 유지 및 잘림 방지 */
}
</style>
