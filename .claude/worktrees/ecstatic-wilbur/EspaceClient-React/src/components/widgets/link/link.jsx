import React from 'react';

function Link({
    href = "#",
    text,
    onClick,
    className="text-decoration-none"
}) {
    const handleClick = (e) => {
        e.preventDefault();
        if(onClick){
            onClick()
        }
    };


    return(
        <a
            href={href}
            onClick={handleClick}
            className={className}
        >
            {text}
        </a>
    );
}

export default Link;