<template>
  <div>
    <span class="title text-h6">내 스터디</span>
    <div class="study-list">
      <v-container>
        <v-row align="center" justify="center">
          <v-col cols="auto">
            <v-radio-group v-model="color" inline>
              <v-radio color="indigo" label="indigo" value="indigo"></v-radio>
              <v-radio
                color="indigo-darken-3"
                label="indigo-darken-3"
                value="indigo-darken-3"
              ></v-radio>
              <v-radio color="primary" label="primary" value="primary"></v-radio>
              <v-radio color="secondary" label="secondary" value="secondary"></v-radio>
            </v-radio-group>
          </v-col>
        </v-row>
        <v-row align="center" justify="center">
          <v-col v-for="(variant, i) in variants" :key="i" cols="auto">
            <v-card :color="color" :variant="variant" class="mx-auto" max-width="344">
              <v-card-item>
                <div>
                  <div class="text-overline mb-1">
                    {{ variant }}
                  </div>
                  <div class="text-h6 mb-1">Headline</div>
                  <div class="text-caption">Greyhound divisely hello coldly fonwderfully</div>
                </div>
              </v-card-item>

              <v-card-actions>
                <v-btn> Button </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </div>
  </div>
</template>

<script setup></script>

<style scoped></style>
