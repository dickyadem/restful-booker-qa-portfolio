import { test, expect } from '@playwright/test';

test.describe('Booking Creation', () => {

test('Complete reservation flow - Single Room', async ({ page }) => {
    await page.goto('/');

    await page.locator('a[href*="/reservation/1"]').click();
    await page.waitForLoadState('networkidle');

    await page.getByRole('button', { name: 'Reserve Now' }).click();

    await page.getByRole('textbox', { name: 'Firstname' }).fill('Ade');
    await page.getByRole('textbox', { name: 'Lastname' }).fill('Mahen');
    await page.getByRole('textbox', { name: 'Email' }).fill('dickyadem@gmail.com');
    await page.getByRole('textbox', { name: 'Phone' }).fill('98787766553421');

    await page.getByRole('button', { name: 'Reserve Now' }).click();
    await page.waitForLoadState('networkidle', { timeout: 15000 });

    // Server kadang crash, handle kedua kemungkinan
    const returnHome = page.getByRole('link', { name: 'Return home' });
    const pageError = page.getByText("This page couldn't load");

    const result = await Promise.race([
        returnHome.waitFor({ timeout: 10000 }).then(() => 'success'),
        pageError.waitFor({ timeout: 10000 }).then(() => 'error'),
    ]);

    expect(result).toBe('success');
});

});