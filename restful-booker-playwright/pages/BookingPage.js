export class BookingPage {
    constructor(page) {
        this.page = page;
    }

    async goto() {
        await this.page.goto('/');
    }
}