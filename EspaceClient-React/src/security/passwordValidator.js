export const validatePassword = (pwd, t) => {
    const errors = [];
    if (!pwd || pwd.length < 8) {
        errors.push(t('password_too_short'));
    }
    if (!pwd || !/[A-Z]/.test(pwd)) {
        errors.push(t('password_uppercase_required'));
    }
    if (!pwd || !/[0-9]/.test(pwd)) {
        errors.push(t('password_digit_required'));
    }
    if (!pwd || !/[^A-Za-z0-9]/.test(pwd)) {
        errors.push(t('password_special_char_required'));
    }
    return errors;
};
