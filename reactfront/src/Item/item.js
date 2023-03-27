import { Route, Routes } from "react-router-dom";

function Item() {
    return (
            <Routes>
            <Route path="/Search" element={<Item />} />
            </Routes>
    );
}

export default Item;