import { Injectable } from '@angular/core';

const FONT_SIZES = [16, 18, 20];
const STORAGE_KEY = 'brasildrop_font_size';

@Injectable({ providedIn: 'root' })
export class FontSizeService {
  private currentIndex = 0;

  constructor() {
    const saved = localStorage.getItem(STORAGE_KEY);
    if (saved) {
      const index = FONT_SIZES.indexOf(Number(saved));
      this.currentIndex = index >= 0 ? index : 0;
    }
    this.apply();
  }

  increase(): void {
    if (this.currentIndex < FONT_SIZES.length - 1) {
      this.currentIndex++;
      this.apply();
    }
  }

  decrease(): void {
    if (this.currentIndex > 0) {
      this.currentIndex--;
      this.apply();
    }
  }

  get canIncrease(): boolean { return this.currentIndex < FONT_SIZES.length - 1; }
  get canDecrease(): boolean { return this.currentIndex > 0; }

  private apply(): void {
    const size = FONT_SIZES[this.currentIndex];
    document.documentElement.style.fontSize = `${size}px`;
    localStorage.setItem(STORAGE_KEY, String(size));
  }
}