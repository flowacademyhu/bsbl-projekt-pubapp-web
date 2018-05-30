import React from 'react';

const FormFields = (props) => {
  const renderFields = () => {
    const formArray = [];

    for (let elementName in props.formData) {
      formArray.push({
        id: elementName,
        settings: props.formData[elementName]
      });
    }

    return formArray.map((item, i) => {
      return (
        <div key={i} className='form_element' >
          {renderTemplates(item)}
        </div>
      );
    });
  };

  const showLabel = (show, label) => {
    return show
      ? <label>{label}</label>
      : null;
  };

  const changeHandler = (event, id) => {
    const newState = props.formData;
    newState[id].value = event.target.value;
    let validData = validate(newState[id]);
    newState[id].valid = validData[0];
    newState[id].validationMessage = validData[1];
    props.change(newState);
  };
  const validate = (element) => {
    console.log(element);
    let error = [true, ''];
    if (element.validation.minLen) {
      const valid = element.value.length >= element.validation.minLen;
      const message = `${!valid ? 'Must be gretaer than' + element.validation.minLen : ''}`;
      error = !valid ? [valid, message] : error;
    }
    if (element.validation.required) {
      const valid = element.value.trim() !== '';
      const message = `${!valid ? 'This field is required' : ''}`;
      error = !valid ? [valid, message] : error;
    }
    return error;
  };
  const showValidation = (data) => {
    let errorMessage = null;
    if (data.validation && !data.valid) {
      errorMessage = (
        <div className='label_error'>
          {data.validationMessage}
        </div>
      );
    }
    return errorMessage;
  };

  const renderTemplates = (data) => {
    let formTemplate = '';
    let values = data.settings;
    switch (values.element) {
      case ('input'):
        formTemplate = (
          <div>
            {showLabel(values.label, values.labelText)}
            <input
              {...values.config}
              value={values.value}
              onChange={
                (event) => changeHandler(event, data.id)
              }
            />
            {showValidation(values)}
          </div>
        );
        break;
      default:
        formTemplate = null;
    }
    return formTemplate;
  };

  return (
    <div>
      {renderFields()}
    </div>

  );
};

export default FormFields;
