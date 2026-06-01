describe('Restful Booker - UI Tests', () => {
  beforeEach(() => {
    cy.visit('/')
  });

  it('Homepage load berhasil', () => {
    cy.title().should('include', 'Restful-booker-platform')
    cy.get('h1').should('contain', 'Welcome to Shady Meadows')
  })

  it('Rooms section tampil', () => {
    cy.get('h2').contains('Our Rooms').should('be.visible')
    cy.get('a[href*="/reservation/"]').should('have.length.greaterThan', 0)
  })

  it('Book now navigates ke reservation page', () => {
    cy.get('a[href*="/reservation/"]').first().click()
    cy.url().should('include', '/reservation/')
  })

  it('Complete reservation flow', () => {
    cy.get('a[href*="/reservation/1"]').click()
    cy.get('button').contains('Reserve Now').click()
    cy.get('input[name="firstname"]').type('Ade')
    cy.get('input[name="lastname"]').type('Mahen')
    cy.get('input[name="email"]').type('test@example.com')
    cy.get('input[name="phone"]').type('08123456789')
    cy.get('button').contains('Reserve Now').click()
    cy.contains('Return home', { timeout: 15000 }).should('be.visible')
  })

  it('coba', function() {
    cy.get('#navbarNav a[href="/#location"]').click();
    cy.get('[data-testid="ContactName"]').click();
    cy.get('[data-testid="ContactName"]').type('aa');
    cy.get('#contact div:nth-child(2)').click();
    cy.get('[data-testid="ContactEmail"]').click();
    cy.get('[data-testid="ContactEmail"]').type('a@gmail.com');
    cy.get('[data-testid="ContactPhone"]').click();
    cy.get('[data-testid="ContactPhone"]').type('123123123');
    cy.get('[data-testid="ContactSubject"]').click();
    cy.get('[data-testid="ContactSubject"]').type('1312312');
    cy.get('[data-testid="ContactDescription"]').click();
    cy.get('[data-testid="ContactDescription"]').type('13123123');
    cy.get('#contact button.btn').click();
    cy.get('#contact p:nth-child(2)').click();
  });
})