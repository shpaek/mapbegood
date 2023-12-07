// EventBus.js
import { ref } from 'vue';

export const EventBus = ref({
  event: null,
  emit(value) {
    this.event = value;
  },
});
