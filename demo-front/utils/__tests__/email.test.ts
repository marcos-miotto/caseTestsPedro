import { validateEmail } from "../email";

describe("Email Utility Tests", () => {
  it("should validate email format", () => {
    const validEmail = "test@example.com";
    const invalidEmail = "test@.com";
    expect(validateEmail(validEmail)).toBe(true);
    expect(validateEmail(invalidEmail)).toBe(false);
  });

  it("should return false for invalid email", () => {
    const invalidEmail = "invalid-email-format";
    expect(validateEmail(invalidEmail)).toBe(false);
  });

  it("should return true for valid email", () => {
    const anotherValidEmail = "user.name+tag+sorting@example.com";
    expect(validateEmail(anotherValidEmail)).toBe(true);
  });
});
