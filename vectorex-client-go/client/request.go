package client

import (
	"bytes"
	"encoding/json"
	"io"
)

func marshalRequestBody(reqBody interface{}) (io.Reader, error) {
	if reqBody == nil {
		return nil, nil
	}
	jsonBody, err := json.Marshal(reqBody)
	if err != nil {
		return nil, err
	}
	return bytes.NewBuffer(jsonBody), nil
}
